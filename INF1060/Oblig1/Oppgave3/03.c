#include <stdio.h>

struct datetime{
	int aar;
	int maaned;
	int dato;
	int tidspunkt; // timer/minutter

};

void init_datetime(struct datetime* dt, int a, int m, int d, int t){

	datetime_set_date(dt,a,m,d);
	datetime_set_time(dt,t);

}

void datetime_set_date(struct datetime* dt, int a, int m, int d){
	dt->aar = a;
	dt->maaned = m;
	dt->dato = d;
}

void datetime_set_time(struct datetime* dt, int t){
	dt->tidspunkt = t;
}

void datetime_diff(struct datetime* dt_from, struct datetime* dt_to, struct datetime* dt_res){

	int aar_forskjell = dt_to->aar - dt_from->aar;
	int maaned_forskjell = dt_to->maaned - dt_from->maaned;
	int dato_forskjell = dt_to->dato - dt_from->dato;

	int tidspunkt_from = dt_from->tidspunkt;
	int tidspunkt_to = dt_to->tidspunkt;
	int timer_to = tidspunkt_to / 100;
	int timer_from = tidspunkt_from / 100;
	int timer_forskjell = timer_to - timer_from;
	if(timer_forskjell < 0 ){
		timer_forskjell += 24;
		dato_forskjell--;
	}
	int minutter_forksjell = (tidspunkt_to - (timer_to * 100)) - (tidspunkt_from - (timer_from * 100));
	if(minutter_forksjell < 0){
		minutter_forksjell += 60;
		timer_forskjell--;
	}
	int tidspunkt_forskjell = (timer_forskjell * 100) + minutter_forksjell;

	if(dato_forskjell < 0){
		dato_forskjell += 30;
		maaned_forskjell--;
	}
	if(maaned_forskjell < 0){
		maaned_forskjell += 12;
		aar_forskjell--;
	}

	datetime_set_date(dt_res, aar_forskjell, maaned_forskjell, dato_forskjell);
	datetime_set_time(dt_res, tidspunkt_forskjell);
}


int main(){

	struct datetime dt_from, dt_to, dt_res;
	init_datetime(&dt_from, 2016,6,12, 1013);
	init_datetime(&dt_to, 2016,10,22, 2200);

	datetime_diff(&dt_from, &dt_to, &dt_res);

	printf("Aar forksjell: %d\n", dt_res.aar);
	printf("Maaned forksjell: %d\n", dt_res.maaned);
	printf("Dato forksjell: %d\n", dt_res.dato);
	printf("Timer og minutter forskjell: %d (xxTimer, yyMinutter)\n", dt_res.tidspunkt);



	return 0;
}