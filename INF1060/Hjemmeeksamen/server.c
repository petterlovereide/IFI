#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <string.h>
#include <errno.h>
#include <signal.h>

#define MAX_CHAR 256

static int tilkoblet = 0;
static int server_sock, client_sock;
static FILE *job_file;


/*	Metode for aa rydde opp og Avslutte
 *	Tar inn en melding msg, som kan vaere T eller E
 *	E er for error, da skal programmet avsluttes med en gang
 *	T er ikke feil, og det skal bare ryddes
 */
void quit(char* msg){ // E=feil T=Trygg
	tilkoblet = 0;
	if(job_file != NULL){
		fclose(job_file);
	}
	close(server_sock);
	close(client_sock);
	printf("%s\n", "Avslutter server");
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
/*	Metode for aa aapne en fil. Tar imot filnavn som argument.
 *	Returnerer en FILE.
 */
FILE *open_file(char* file_name){
	FILE *file;
	if((file = fopen(file_name, "r+b")) == NULL){
		perror("fopen()");
		quit("E");
	}
	return file;
}
/*	Metode for aa opprette socket til server.
 *	Tar imot Port som argument. Oppretter forst en socket,
 *	Deretter binder den seg til porten. Returnerer til slutt en socket
 */
int create_socket(char *port){
	int sock = socket(AF_INET, SOCK_STREAM, 0);
	if (sock == -1) {
		perror("socket()");
		return -1;
	}

	short port_num = atoi(port);

	struct sockaddr_in server_addr;
	memset(&server_addr, 0, sizeof(struct sockaddr_in));
	server_addr.sin_family = AF_INET;
	server_addr.sin_addr.s_addr = INADDR_ANY;
	server_addr.sin_port = htons(port_num);

	int yes = 1;
	if (setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int))) {
		perror("setsockopt()");
		return -1;
	}

	if (bind(sock, (struct sockaddr *)&server_addr, sizeof(server_addr))) {
		perror("bind()");
		return -1;
	}
	printf("Koblet til port %d\n", port_num);

	if (listen(sock, SOMAXCONN)) {
		perror("listen()");
		return -1;
	}

	return sock;
}

/*	Metode for aa godta tilkobling fra klient.
 *	oppretter en socket og returnerer den naar metoden er ferdig.
 *	Setter tilkoblet = 0.
 */
int accept_connection(int s_sock){
	struct sockaddr_in client_addr;
	memset(&client_addr, 0, sizeof(client_addr));
	socklen_t addr_len = sizeof(client_addr);

	int c_sock = accept(s_sock, (struct sockaddr *)&client_addr, &addr_len);
	if (c_sock == -1) {
		if (errno == EINTR)
			return 0;
		perror("accept()");
		return -1;
	}

	char *client_ip = inet_ntoa(client_addr.sin_addr);
	printf("Klient koblet til fra IP %s\n", client_ip);

	tilkoblet = 1;
	return c_sock;
}

/*	Metode for aa kommunisere med klient. Naar tilkoblet lytter serveren etter
 *	melding fra klient.
 */
void communicate(){
	int end_of_file = 0;
	char msg[MAX_CHAR] = { 0 };
	ssize_t ret;

	while(tilkoblet){
	  ret = recv(client_sock, msg, sizeof(msg) -1, 0);
		if(ret == -1){
			perror("recv()");
			quit("E");
		}
		/*	Naar det er en melding. G betyr at det skal sendes ett visst antall jobber.
		 *	Andre char i meldingen er da hvor mange jobber det skal sendes. 0 betyr alle.
		 *	Starter for lokke som leser inn antall jobber. Sjekker om EOF eller om en
		 *	jobbtype er feil. Hvis ikke legges den til i msg_collection som er en string
		 *	sammensatt av alle jobbene som skal sendes. Naar alt er lest inn, sendes det
		 *	til klienten. Hvis meldingen er T eller E har klienten konlet fra.
		 */
	  else if(ret > 0){
			if(msg[0] == 'G'){
				unsigned char number_of_jobs_char = msg[1];
				int number_of_jobs = (int) number_of_jobs_char - 48;
				char *msg_collection = malloc(sizeof(char) * 2);

				int antall_jobber_sendt = 0;
				int index = 0;
				for(int i = 0; (i < number_of_jobs) || (number_of_jobs == 0); i++){
					char job_type;
					if((job_type = getc(job_file)) == EOF){
						end_of_file = 1;
						printf("%s\n", "	Slutt paa fil.");
						msg_collection = realloc(msg_collection, sizeof(char) * (index + 2));
						msg_collection[index] = 'Q';
						msg_collection[index + 1] = '\0';
						tilkoblet = 0;
						break;
					}
					else if(job_type != 'E' && job_type != 'O' && job_type != 'Q'){
						printf("%s\n", "	Feil i fil.");
						char end[2] = { 'Q', '\0' };
						ret = send(client_sock, end, strlen(end), 0);
						quit("E");
					}

					else{
						unsigned char job_length = getc(job_file);
						msg_collection = realloc(msg_collection, sizeof(char) * (index + job_length + 2));

						msg_collection[index] = job_type;
						msg_collection[index + 1] = job_length;

						for(int j = index + 2 ; j < job_length + index + 2; j++){
								if((msg_collection[j] = getc(job_file)) == EOF){
									printf("%s\n", "	Feil i fil.");
									char end[2] = { 'Q', '\0' };
									ret = send(client_sock, end, strlen(end), 0);
									quit("E");
								}
						}

						antall_jobber_sendt++;
						index += job_length + 2;
					}
				}

				ret = send(client_sock, msg_collection, strlen(msg_collection), 0);
				free(msg_collection);
				printf("	Antall jobber sendt: %d\n", antall_jobber_sendt);

				/*	Hvis det er EOF, venter serveren paa at klienten skal koble fra,
				 *	og deretter kobler den selv fra.
				 */
				if(end_of_file){
					printf("%s\n", "	Venter paa at klient skal koble fra...");
					ret = recv(client_sock, msg, sizeof(msg) - 1, 0);
					if(ret == -1){
						perror("recv()");
						quit("E");
					}
					else if(ret > 0){
						if(msg[0] == 'T'){
							printf("%s\n", "	Klient koblet fra.");
							tilkoblet = 0;
							break;
						}
					}
				}

			}
	    else if(strcmp(msg,"T") == 0){
	      printf("%s\n", "	Klient koblet fra.");
				tilkoblet = 0;
				break;
	    }
	    else if(strcmp(msg,"E") == 0){
	      printf("%s\n", "	Klient koblet fra pga feil.");
				quit("E");
	    }
	  }
	  else{
			if(!EOF){
				tilkoblet = 0;
				break;
			}
	  }
	}
}


int main(int argc, char *argv[]){

	/*	Sjekker at antall argumenter er riktig.
	 *	sigaction struct for aa oppdage signaler som CTRL+C.
	 *	Oppretter server socket med create_socket()
	 *	Aapner fil med open_file()
	 *	Godtar tilkobling til klient med accept_connection()
	 *	Starter communicate() og tilslutt avslutter med quit()
	 */

	if(argc != 3){
		printf("%s\n", "server <filnavn> <port>");
	}

	struct sigaction sa;
	memset(&sa, 0, sizeof(sa));
	sa.sa_handler = handler;

	if (sigaction(SIGINT, &sa, NULL)) {
		perror("sigaction()");
		quit("E");
	}

	if((server_sock = create_socket(argv[2])) == -1){
		quit("E");
	}

	job_file = open_file(argv[1]);

	if((client_sock = accept_connection(server_sock)) == -1){
		quit("E");
	}

	communicate();

	quit("T");
	return 0;
}
