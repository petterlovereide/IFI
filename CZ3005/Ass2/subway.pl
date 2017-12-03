option(breads([parmesan, honeywheat, white])).
main([chicken, tuna, turkey, ham]).
veggis([lettuce, tomato, onion, pickle]).
sauce([mustard, chipotle, bbq]).
sides([soup, soda, cookie]).

options :- print_list(option(bread)).

print_list(L) :-
    write('X='),
    print_list_aux(L), !.
print_list_aux([H1,H2|T]) :-
    print(H1),
    write(','),
    print_list_aux([H2|T]).
print_list_aux([X]) :-
    print(X),
    nl.
print_list_aux([]) :- nl.

test :-
    write("Type the word : "),
    read(Input),
    write(Input).
