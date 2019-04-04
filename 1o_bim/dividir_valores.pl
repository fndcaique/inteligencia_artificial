

div([], _, [], []).
div([H|T], V, LME, LM) :- V =< H, !, div(T, V, L2, LM), LME = [H|L2];
						div(T, V, LME, L2), LM = [H|L2].


rprint([]).
rprint([H|T]) :- rprint(T), write(H), nl.


