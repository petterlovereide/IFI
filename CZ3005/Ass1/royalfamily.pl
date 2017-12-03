% persons
female(elizabeth).
female(ann).
male(charles).
male(andrew).
male(edward).

% child, parent
child(andrew, elizabeth).
child(ann, elizabeth).
child(charles, elizabeth).
child(edward, elizabeth).
% 1st child older than 2nd
older(charles, ann).
older(ann, andrew).
older(andrew, edward).

% is X older than Y
isOlder(X, Y) :-
    older(X, Y) ;
    older(X, Z), isOlder(Z, Y).

% person, country
monarch(elizabeth, uk).

% country, value out. MAIN METHODE: Call with succession(uk, X).
succession(Country, List) :-
    monarch(Monarch, Country),
    findall(Child, child(Child, Monarch), Children), sortList(Children, List).

% does X have a weaker claim than Y
weakerClaim(X, Y) :-
    % old succession rules
    male(Y), female(X) ;
    male(X), male(Y), isOlder(Y, X) ;
    female(X), female(Y), isOlder(Y, X).
    % new successoin rules
    % isOlder(Y, X).

% sorts and inserts child into successionline
sortList([Child|Tail], SortedList) :-
    sortList(Tail, TailSortedList),
    insert(Child, TailSortedList, SortedList).
sortList([], []).

% inserts child into successionline
insert(Child, [H|T1], [H|T2]) :-
    weakerClaim(Child, H), !,
    insert(Child, T1, T2).
insert(Child, T1, [Child|T1]).
