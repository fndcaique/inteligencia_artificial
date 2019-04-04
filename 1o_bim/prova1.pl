
cotacao(dolar, real, 4).
cotacao(euro, tustao, 10).
cotacao(real, tustao, 2).
cotacao(tupi, dolar, 2).

calcula(X, Y, C) :- cot(X, Y, C, 1, [X]).

cot(X, Y, C, V, _) :- cotacao(X, Y, R), C is V * R, !.
cot(X, Y, C, V, _) :- cotacao(Y, X, R), C is V / R, !.
cot(X, Y, C, V, Cam) :- cotacao(Z, X, R), not(member(Z, Cam)),
  cot(Z, Y, C, V/R, [Z|Cam]).
cot(X, Y, C, V, Cam) :- cotacao(X, Z, R), not(member(Z, Cam)),
  cot(Z, Y, C, V * R, [Z|Cam]).