drop table if exists Prosjektarbeid;
drop table if exists Prosjekt;
drop table if exists Ansatt;
drop table if exists Lonnstrinn;

create table Lonnstrinn (
	lonnstrinn_nr int primary key,
	ukelonn int,
	timelonn_vanlig int,
	timelonn_overtid int
);

create table Ansatt (
	ansatt_id int primary key,
	navn text not null,
	type_ansatt text not null,
	lonnstrinn_nr int references Lonnstrinn(lonnstrinn_nr),
	jobbtimer int,
	check (type_ansatt = 'fast_heltid' or type_ansatt = 'fast_deltid' or type_ansatt = 'timebasis')
);

create table Prosjekt (
	prosjekt_kode int primary key,
	ansatt_id int references Ansatt(ansatt_id),
	tid_ramme int

);

create table Prosjektarbeid (
	prosjekt_kode int references Prosjekt(prosjekt_kode),
	ansatt_id int references Ansatt(ansatt_id),
	tid_max int not null
);

insert into Lonnstrinn (lonnstrinn_nr, ukelonn, timelonn_vanlig, timelonn_overtid)
values (0, 4500, 150, 225), (1, 6000, 200, 300), (2, 6900, 230, 350);

insert into Ansatt (ansatt_id, navn, type_ansatt, lonnstrinn_nr, jobbtimer)
values (0, 'Per', 'fast_heltid', 0, 30), (1, 'Hans', 'fast_deltid', 1, 15), (2, 'Heidi', 'fast_heltid', 1, 30), (3, 'Kurt', 'timebasis', 0, 0), (4, 'Truls', 'timebasis', 1, 0),
(5, 'Nils', 'fast_deltid', 2, 20), (6, 'Turid', 'timebasis', 2, 0);

-- Her er ansatt_id prosjektleder
insert into Prosjekt (prosjekt_kode, ansatt_id, tid_ramme)
values (0, 0, 1000), (1, 0, 2000), (2, 2, 500);

-- Her er ansatt_id arberider paa prosjekt
insert into Prosjektarbeid (prosjekt_kode, ansatt_id, tid_max)
values (0, 1, 200), (1, 1, 100), (2, 0, 400);

-- Oppgave 3.1 Prosjektledere som er ledere paa flere prosjekter
select p.ansatt_id, a.navn 
	from Prosjekt p, Ansatt a 
	where p.ansatt_id = a.ansatt_id 
	group by p.ansatt_id, a.navn 
	having count(p.ansatt_id) > 1;

-- Oppgave 3.2 Prosjektledere som jobber paa andre prosjekter
select  a.navn, p.prosjekt_kode prosjektleder, pa.prosjekt_kode prosjektarbeid 
	from Prosjekt p, Ansatt a, Prosjektarbeid pa 
	where p.ansatt_id = pa.ansatt_id 
	and p.ansatt_id = a.ansatt_id 
	and pa.ansatt_id = a.ansatt_id;

-- Oppgave 3.3, Hoyeste lonnstrinn nr.
select a.lonnstrinn_nr, a.navn 
	from Ansatt a 
	inner join (select a.navn, MAX(l.lonnstrinn_nr) as max_l_nr 
	from Ansatt a, Lonnstrinn l group by navn) ajoin 
	on a.navn = ajoin.navn 
	and a.lonnstrinn_nr = ajoin.max_l_nr 
	and a.type_ansatt = 'timebasis';

-- Oppgave 3.4, Gjennomsnittlonn per uke for fast ansatte med et visst antall jobbtimer
select  a.jobbtimer, round((avg(l.ukelonn) / 30 * a.jobbtimer), 2) gjennomsnitt_per_uke
	from Ansatt a, Lonnstrinn l 
	where a.type_ansatt = 'fast_heltid' 
	or a.type_ansatt = 'fast_deltid' 
	and l.lonnstrinn_nr = a.lonnstrinn_nr 
	group by a.jobbtimer 
	order by a.jobbtimer DESC;

