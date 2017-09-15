#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct ruter{

	unsigned char ruterID;
	char FLAGG;
	unsigned char produsent_lengde;
	char* produsent;

};

void set_struct_data(struct ruter* r, unsigned char rID, char flagg, unsigned char pl, char* p){
	r->ruterID = rID;
	r->FLAGG = flagg;
	r->produsent_lengde = pl;
	r->produsent = p;
}

void print_router_info(int i, struct ruter* r[]){
	//printf("Router id: %d %d\n", i, r[i]->ruterID);
	//printf("Router info: %s\n", r[i]->produsent);

	for(int i = 0; i < 5; i++){
		printf("%s\n", r[i]->produsent);
	}

}

int main(int argc, char *argv[]){
	if(argc != 2){
		printf("%s\n", "Feil antall argumenter. ");
	}

	FILE *fileIn;
	fileIn = fopen(argv[1], "r");
	if( fileIn == NULL ){
		printf("%s\n", "Feil med innlasting av fil.");
	}

	char str[256];
	int antall_router;
	if( fgets(str, sizeof(str), fileIn) != NULL ){
		antall_router = (int) str[0];
		printf("Number of routers: %d\n", antall_router);
	}

	struct ruter* ruterSamling[antall_router];

	char* sPeker;
	for(int i = 0; i < antall_router; i++){
		int router_number = fgetc(fileIn);
		int router_flagg = fgetc(fileIn);
		int lengde_produsent = fgetc(fileIn);
		char pString[lengde_produsent];
		for(int i = 0; i < lengde_produsent - 1; i++){
			pString[i] = (char) fgetc(fileIn);
		}
	  sPeker = &pString;
		fgets(str, sizeof(str), fileIn);

		struct ruter* newRouter = malloc(sizeof(struct ruter));
		set_struct_data(newRouter, router_number, router_flagg, lengde_produsent, sPeker);
		ruterSamling[router_number] = newRouter;
		printf("Router info: %d %d %d %s\n", router_number, router_flagg, lengde_produsent, pString);
		printf("Router info: %d %s\n", ruterSamling[i]->ruterID, ruterSamling[i]->produsent);
	}


	printf("Tilgjengelige kommandoer: %s\n", "Print info");
	char* input;
	scanf("%s", input);

	if(strcmp("Print info",input) == 1){
		printf("%s\n", "Skriv inn router nummer: ");
		char* s;
		fflush(stdin);
		scanf("%s", &s);
		int rid = atoi(&s);
		if(rid >= 0 && rid < antall_router){
			print_router_info(rid, ruterSamling);
		}
		else{
			printf("%s\n", "Routeren finnes ikke.");
		}
	}
	else{
		printf("%s\n", "Kommando finnes ikke.");
	}

	fclose(fileIn);
	return 1;
}
