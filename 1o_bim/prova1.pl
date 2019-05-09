cotacao(dolar, real, 4).
cotacao(euro, tustao, 10).
cotacao(real, tustao, 2).
cotacao(tupi, dolar, 2).

calcula(X, Y, C) :- cot(X, Y, C, [X]).

cambio(X, Y, Z) :- cotacao(X, Y, Z).
cambio(X, Y, Z) :- cotacao(Y, X, R), Z is 1/R.

cot(X, Y, C, _) :- cambio(X, Y, C).
cot(X, Y, C, _) :- cambio(Y, X, C).
cot(X, Y, C, Cam) :- cambio(X, Z, R), not(member(Z, Cam)),
  cot(Z, Y, V, [Z|Cam]), C is V * R.


 remove(L,L1, R) :- retira(L, L1, R, 1).
 retira([],[],[],_).
 retira([H|T],[C|K], R, P) :- P = C, !, retira(T, K, R2, P+1), R = [H,R2];
 	retira(T, [C|K], R, P+1).