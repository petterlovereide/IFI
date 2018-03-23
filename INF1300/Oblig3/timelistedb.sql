drop view if exists Timeantall;
drop view if exists Varighet;
drop table if exists Timelistelinje;
drop table if exists Timeliste;

create table Timeliste (
  timelistenr int primary key,
  status text not null,
  levert date,
  utbetalt date,
  beskrivelse text not null,
  check (status = 'aktiv' or status = 'levert' or status = 'utbetalt')
);

create table Timelistelinje (
  timelistenr int references Timeliste(timelistenr),
  linjenr int,
  startdato date not null,
  starttid time not null,
  sluttid time,
  pause int,
  beskrivelse text not null,
  primary key (timelistenr, linjenr)
);

create view Varighet AS
  select timelistenr,
         linjenr, 
         (sluttid - starttid - pause) as varighet
  from (select timelistenr,
               linjenr,
               cast(extract(hour from starttid) as integer)*60 +
                    cast(extract(minute from starttid) as integer) as starttid,
               cast(extract(hour from sluttid) as integer)*60 +
                    cast(extract(minute from sluttid) as integer) +
                         case when sluttid < starttid then 60*24
                              else 0
                         end as sluttid,
               case when pause is null then 0
                    else pause
               end as pause
        from Timelistelinje
        where sluttid is not null) as c;

create view Timeantall AS
  select timelistenr, ceil(cast(minuttantall as real)/60) as timeantall
  from (select timelistenr, sum(varighet) as minuttantall
        from Varighet
        group by timelistenr) as m;

\copy Timeliste from 'timeliste.txt' with delimiter '|' null ''

\copy Timelistelinje from 'timelistelinje.txt' with delimiter '|' null ''


select linjenr Oppgave21 from Timelistelinje where timelistenr = 3;

select COUNT(timelistenr) Oppgave22 from Timeliste;

select COUNT(timelistenr) Oppgave23 from Timeliste where status != 'utbetalt';

select COUNT(timelistenr) Oppgave24 from Timelistelinje where sluttid - starttid < '00:00'; 

select sum(timeantall) Oppgave25 from Timeantall, Timeliste where status != 'utbetalt' and Timeliste.timelistenr = Timeantall.timelistenr;

select sum(timeantall) Oppgave26 from Timelistelinje, Timeantall where extract('month' from startdato) = 7  and Timelistelinje.timelistenr = Timeantall.timelistenr;

insert into Timeliste(timelistenr, status, levert, utbetalt, beskrivelse)
values( 8, 'utbetalt', '2016-07-29', '2016-08-10', 'Planlegging av neste trinn'),
( 9, 'levert', '2016-08-03', null, 'Planlegging av neste trinn'),
( 10, 'aktiv', null, null, 'Skriving av rapport');

insert into Timelistelinje(timelistenr, linjenr, startdato, starttid, sluttid, pause, beskrivelse)
values (8, 1, '2016-07-25', '10:15', '17:30', 50, 'diskusjoner'),
(8, 2, '2016-07-27', '12:45', '14:00', null, 'kronkretisonering'), 
(9, 1, '2016-07-27', '15:15', '18:45', 70, 'del1'),
(9, 2, '2016-07-28', '10:00', '14:00', 35, 'del2'),
(9, 3, '2016-07-28', '21:00', '04:15', 90, 'del3'),
(9, 4, '2016-08-02', '13:00', '17:00', null, 'del4'),
(10, 1, '2016-08-03', '10:50', '16:10', 40, 'kap1'),
(10, 2, '2016-08-05', '18:00', null, null, 'kap2');

select timelistenr, status, levert, utbetalt, beskrivelse from Timeliste where timelistenr >= 8;

select timelistenr, linjenr, startdato, starttid, sluttid, pause, beskrivelse from Timelistelinje where timelistenr >= 8;



