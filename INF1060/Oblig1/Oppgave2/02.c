#include <string.h>
#include <ctype.h>
#include <stdio.h>

int stringsum(char* s){
	int lengde = 0;
	for(int i = 0; i < strlen(s); i++){
		int asciiVerdi = (int) s[i];
		if((asciiVerdi >= 65 && asciiVerdi <= 90) || (asciiVerdi >= 97 && asciiVerdi <= 122)){
			lengde += (int) tolower(s[i]) - 96;
		}
		else{
			return -1;
		}
	}
	return lengde;
}

int distance_between(char* s, char c){
	int nr1 = -1;
	int nr2 = -1;

	for(int i = 0; i < strlen(s); i++){
		if(s[i] == c){
			if(nr1 == -1){
				nr1 = i;
			}
			else{
				nr2 = i;
				break;
			}
		}
	}
	if(nr2 >= 0){
		return nr2 - nr1;
	}
	return -1;
}

char* string_between(char* s, char c){
	int db = distance_between(s,c);
	if(db == -1){
		return NULL;
	}
	char* str = malloc((db) * sizeof(char));

	for(int i = 0; i < strlen(s); i++){
		if(s[i] == c){
			for(int j = 0; j < (db- 1); j++){
				str[j] = s[i+j+1];
				if(j == db - 2){
					str[j+1] = '\0';
				}
			}
			return str;
		}
	}
	return str;
}

char** split(char* s){
	int antallOrd = 1;
	for(int i = 0; i < strlen(s); i++){
		if(s[i] == ' '){
			antallOrd++;
		}
	}
	char** str = malloc(antallOrd * sizeof(char*));

	int ordNummer = 0;
	int charNummer = 0;

	for(int i = 0; i < antallOrd; i++){
		str[i] = malloc(sizeof(char*));
	}

	for(int i = 0; i < strlen(s); i++){

		str[ordNummer][charNummer] = s[i];
		charNummer++;

		if(s[i+1] == ' ' || i+1 == strlen(s)){
			str[ordNummer][charNummer] = '\0';
			ordNummer++;
			charNummer = 0;
			i++;
		}
	}
	str[ordNummer] = '\0';
	return str;
}

void stringsum2(char* s, int* res){
	int sum = stringsum(s);
	*res = sum;
}