CC=gcc
CFLAGS=-Wall -Wextra -std=gnu99 -g

all: klient server

klient: klient.c
	$(CC) $(CFLAGS) $^ -o $@

server: server.c
	$(CC) $(CFLAGS) $^ -o $@

clean:
	rm -f server klient
