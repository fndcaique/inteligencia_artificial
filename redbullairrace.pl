%FATOS

piloto(lamb).
piloto(besenyei).
piloto(chambliss).
piloto(maclean).
piloto(mangold).
piloto(jones).
piloto(bonhomme).

equipe(breitling).
equipe(redbull).
equipe(mediterranean_racing).
equipe(cobra).
equipe(matador).

circuito(istanbul).
circuito(budapest).
circuito(porto).



%REGRAS

participa(lamb,breitling).
participa(besenyei,redbull).
participa(chambliss,redbull).
participa(maclean,mediterranean_racing).
participa(mangold,cobra).
participa(jones,matador).
participa(bonhomme,matador).

venceu(jones,porto).
venceu(mangold,budapest).
venceu(mangold,istanbul).

gates(istanbul,9).
gates(budapest,6).
gates(porto,5).


%RELAÇÕES


pilota(X,mx2) :- piloto(X), X == lamb.
pilota(X,edge540) :- piloto(X), X \= lamb.

% Uma equipe ganha uma corrida quando um dos seus pilotos vence essa corrida
ganhou(X,Y) :- equipe(X), circuito(Y), piloto(Z), venceu(Z,Y), participa(Z,X).
