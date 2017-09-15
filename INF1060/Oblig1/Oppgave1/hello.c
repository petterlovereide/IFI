#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdarg.h>

int main(int antArgs, char *args[]){
  int i = antArgs - 1;
  printf("%s\n",args[i]);
  return 0;
}
