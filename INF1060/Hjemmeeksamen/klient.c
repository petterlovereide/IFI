#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <string.h>
#include <fcntl.h>
#include <signal.h>
#include <netdb.h>

#define MAX_CHAR 256

static int fds_1[2], fds_2[2];
static pid_t pid_1, pid_2;
static int tilkoblet = 0;
static int sock;
static ssize_t ret;

/*	Metode for aa rydde opp og Avslutte
 *	Tar inn en melding msg, som kan vaere T eller E
 *	E er for error, da skal programmet avsluttes med en gang
 *	T er ikke feil, og det skal bare ryddes
 */
void quit(char* msg){
	ret = send(sock, msg, sizeof(char), 0);
	tilkoblet = 0;
	kill(pid_1, SIGKILL);
	kill(pid_2, SIGKILL);
	close(fds_1[0]);
	close(fds_1[1]);
	close(fds_2[0]);
	close(fds_2[1]);
	close(sock);
	printf("%s\n", "Avslutter klient");
	if(strcmp(msg, "E") == 0){
		exit(EXIT_FAILURE);
	}
}

/*	Handler for signaler
 *	Starter quit() med E, og dermed avslutter programmet
 */
void handler(int signum)
{
	printf("Signal %d oppdaget.\n", signum);
	quit("E");
}

/*	Metode for aa hente IP addressen til et hostname
 *	Tarinn hostname som argument og returnerer en IP
 *	Henter kun IPv4 adresser
 *	Hvis et hostname har flere IP adresser, returneres den siste
 *	Denne metoden ble skrevet i forelesning
 */
char* get_ip_from_hostname(char* hostname){
	struct addrinfo hints, *result;
	memset(&hints, 0, sizeof(hints));

	hints.ai_socktype = SOCK_STREAM;
	hints.ai_family = AF_INET;

	int ret = getaddrinfo(hostname, NULL, &hints, &result);
	if(ret){
		fprintf(stderr, "getaddrinfo feilet: %s\n", gai_strerror(ret));
		quit("E");
	}

	char *ip;
	struct addrinfo *rp;
	for(rp = result; rp != NULL; rp = rp->ai_next){
		if(rp->ai_family != AF_INET){
			continue;
		}

		struct sockaddr_in *addr = (struct sockaddr_in *)rp->ai_addr;
		ip = inet_ntoa(addr->sin_addr);
	}

	freeaddrinfo(result);
	return ip;
}

/*	Metode for aa snakke med server
 *	Tar inn en socket, en msg (T, G), og antall jobber
 *	Hvis antall jobber er 0, skal alle jobber hentes fra server
 *	Dermed sendes melding til server. Get meldinger er 2 Char lange.
 *	De bestaar av G + char antall jobber
 *	Det betyr at antall jobber som kan hentes paa en gang, uten aa hente alle
 *	er 256, eller MAX_CHAR skal jeg har opprettet.
 *	Fortsetter inne i metoden:
 */

int get_msg_from_server(int sock, char msg[], int number_of_jobs){
	if(strcmp(msg,"T") == 0){
		return -1;
	}

	unsigned char number_of_jobs_char = number_of_jobs + 48;
	char mid[2] = { 0 };
	mid[0] = msg[0];
	mid[1] = number_of_jobs_char;

	ret = send(sock, mid, sizeof(char) * 2, 0);
	if(ret == -1){
		perror("send()");
		quit("E");
	}

	int max_size = MAX_CHAR * MAX_CHAR * 2;

	char* buf = malloc(sizeof(char) * max_size);
	memset(buf, 0, sizeof(char) * max_size);
	ret = recv(sock, buf, sizeof(char) * max_size, 0);
	if(ret == 0){
		printf("Server koblet fra\n");
		quit("E");
	}
	else if(ret == -1) {
		perror("recv()");
		quit("E");
	}

	/*	Hvis recv var vellykket, starter en for lokke. Den kjorer til vi har
	 *	hentet number of jobs, eller til alle er hentet (number_of_jobs == 0).
	 *	index bestemmer hvor i buf vi skal lese. Buf er alle data vi har faatt fra
	 *	server. Hvis jobb type er O, sendes melding til barn1. Hvis jobb type er E
	 *	sendes jobbtype til barn2. Hvis jobbtype er Q avsluttes programmet. Hvis det
	 *	er noe annet, er det feil i fil.
	 */
	if(ret > 0){
		int index = 0;
		for(int i = 0; (i < number_of_jobs) || (number_of_jobs == 0); i++){
			char job_type = buf[index];
			unsigned char job_length = buf[index + 1];
			index += 2;
			char* job_msg = malloc(sizeof(char) * job_length);
			memset(job_msg, 0, sizeof(char) * job_length);
			memcpy(job_msg, buf + index, sizeof(char) * job_length);
			index += job_length;

			if(job_type == 'O'){
				write(fds_1[1], job_msg, job_length);
			}
			else if(job_type == 'E'){
				write(fds_2[1], job_msg, job_length);
			}
			else if(job_type == 'Q'){
				ret = send(sock, "T", sizeof(char), 0);
				tilkoblet = 0;
				free(job_msg);
				free(buf);
				return -1;
			}
			else{
				printf("%s\n", "Feil i fil");
				break;
			}
			free(job_msg);
			usleep(5000);
		}
		free(buf);
	}
	return 0;
}

/*	Metode for aa koble til server. tar inn IP og Port som argumenter.
 *	Oppretter sockaddr_in struct, setter ip familiy og port
 *	Kobler til med IP og Port, hvis vellyket setter den tilkoblet = 1
 *	og returnerer en socket. Hentet fra forelesning.
 */
int connetct_to_server(char *ip, char *port_in){
	int k_sock;
	int port = atoi(port_in);

	k_sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	if (k_sock == -1) {
		perror("socket()");
		quit("E");
	}

	struct sockaddr_in server_addr;
	memset(&server_addr, 0, sizeof(struct sockaddr_in));
	server_addr.sin_family = AF_INET;
	server_addr.sin_port = htons(port);
	int ip_ret = inet_pton(AF_INET, ip, &server_addr.sin_addr.s_addr);
	if (ip_ret != 1) {
		if (ip_ret == 0) {
			fprintf(stderr, "Ugyldig IP addresse: %s\n", ip);
		} else {
			perror("inet_pton()");
		}
		quit("E");
	}

	printf("Kolber til %s:%d\n", ip, port);
	if (connect(k_sock, (struct sockaddr *)&server_addr, sizeof(server_addr))) {
		perror("connect()");
		quit("E");
	}
  printf("%s\n", "Tilkobling velykket");
	tilkoblet = 1;
	return k_sock;
}

/*	Sjekker at antall argumenter stemmer, oppretter pipes, opretter barn.
 *	Barn1 og Barn2 starter sine oppgaver. pid_1 == 0 er barn1 og pid_2 == 0 er barn2
 */

int main(int argc, char *argv[]){
	if(argc != 3){
		printf("%s\n", "klient <ip> <port>");
	}

	if(pipe(fds_1) == -1 || pipe(fds_2) == -1){
		perror("pipe()");
		quit("E");
	}

	if((pid_1 = fork()) != 0){
		pid_2 = fork();
	}

	if(pid_1 == -1 || pid_2 == -1){
		perror("fork()");
		quit("E");
	}

	if(pid_1 == 0){
		close(fds_1[1]);
		close(fds_2[0]);
		close(fds_2[1]);
		char buf[MAX_CHAR] = { 0 };
		for(;;){
			read(fds_1[0], buf, sizeof(buf));
			fprintf(stdout, "%s\n", buf);
			memset(buf, 0, MAX_CHAR);
		}
	}
	else if(pid_2 == 0){
		close(fds_2[1]);
		close(fds_1[0]);
		close(fds_1[1]);
		char buf[MAX_CHAR] = { 0 };
		for(;;){
			read(fds_2[0], buf, sizeof(buf));
			fprintf(stderr, "%s\n", buf);
			memset(buf, 0, MAX_CHAR);
		}
	}

	/*	Parent: sigaction struct for aa oppdage signaler som CTRL+C.
	 *	Henter ip fra get_ip_from_hostname(), og dermetter kobler til server med
	 *	connetct_to_server().
	 *	Deretter starter menyen.
	 */

	else{
		struct sigaction sa;
		memset(&sa, 0, sizeof(sa));
		sa.sa_handler = handler;

		if (sigaction(SIGINT, &sa, NULL)) {
			perror("sigaction()");
			quit("E");
		}

		close(fds_1[0]);
		close(fds_2[0]);

		char *ip = get_ip_from_hostname(argv[1]);

		sock = connetct_to_server(ip, argv[2]);
		char msg[MAX_CHAR];

		while(tilkoblet){
			printf("Commands: %s: ", "Avslutt = 'q', Hent en jobb = 'G', Hent flere jobber: G+");
			scanf("%s", msg);
			if(strcmp(msg,"G") == 0){
				if(get_msg_from_server(sock, "G", 1) == -1){
					break;
				}
			}
			else if(strcmp(msg,"G+") == 0){
				printf("%s\n", "Antall jobber: 'All' eller et tall: ");
				scanf("%s", msg);
				if(strcmp(msg,"All") == 0){
					if(get_msg_from_server(sock, "G", 0) != -1){
						break;
					}
				}
				else if(atoi(msg) > 0){
					if(atoi(msg) > MAX_CHAR - 1){
						printf("Kan max hente %d\n", MAX_CHAR);
					}
					else{
						printf("Henter %d jobber fra server\n", atoi(msg));
						if(get_msg_from_server(sock, "G", atoi(msg)) == -1){
							break;
						}
					}
				}
				else{
					printf("%s\n", "Ugyldig kommando.");
				}
			}
			else if(strcmp(msg,"q") == 0){
				tilkoblet = 0;
				if(get_msg_from_server(sock, "T", 0) == -1){
					break;
				}
			}
		}
	}

	quit("T");
	return 0;
}
