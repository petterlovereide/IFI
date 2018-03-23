drop view if exists Farge;
drop view if exists Lysforhold;
drop view if exists Pris;
drop view if exists Vare;
drop view if exists Plantesort;

create type lysforhold as enum ('sol', 'halvskygge', 'skygge');
create type fl_type as enum ('løk', 'frø');

create table Plantesort(
	sortsid int primary key,
	latinsk_navn String,
	norsk_navn String
);

create table Farge(
	sortsid int references Plantesort(sortsid),
	farge String
);

create table Lysforhold(
	sortsid int references Plantesort(sortsid),
	lys lysforhold
);

create table Vare(
	varenr int primary key,
	sortsid int references Plantesort(sortsid),
	FL fl_type,
	mengde int,
	pl_fra int check pl_fra <= 12,
	pl_til int check pl_fra <= 12,
	pl_dybde int,
	blomstrer int check pl_fra <= 12
);

create table Pris(
	varenr int references Vare(varenr),
	antall int,
	enhetspris int
);

-- 2a
select varenr from Vare where pl_til > blomstrer;

-- 2b
select count(varenr) from Vare where FL = 'løk' and pl_dybde = null;

-- 2c
select p norsk_navn, p latinsk_navn from Plantesort p, Vare v
	where v.FL = 'løk' and v.FL = 'frø' and v.sortsid = p.sortsid;

-- 2d
select p norsk_navn from Plantesort p, Vare v where v.FL != 'halvskygge'
	and (v.FL = 'sol' and v.FL = 'skygge') group by p.norsk_navn;

-- 2e
select pr max(enhetspris), v varenr, pl latinsk_navn
	from Vare v, Plantesort pl, Pris pr where pr.varenr = v.varenr and pr.antall = 1;

-- 2f
select varenr, max(count(varenr)) from Pris;

-- 2g
select antall * enhetspris from Pris where varenr = 42 and antall = 220;





