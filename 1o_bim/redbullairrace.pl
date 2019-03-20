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



% Escreva as seguintes perguntas em Prolog:

% a) Quem venceu a corrida no Porto?
% venceu(Piloto,porto).

% b) Qual a equipe que ganhou no Porto?
% ganhou(Equipe,porto).

% c) Quais os pilotos que venceram mais de um circuito?

countXinList(_,[],0).
countXinList(X,[X|T],C) :- !,countXinList(X,T,C1), C is C1+1.
countXinList(X,[_|T],C) :- countXinList(X,T,C).

% d) Que circuitos têm mais de 8 gates?
% findall((Circuito,QtdeGates), (gates(Circuito,QtdeGates), QtdeGates > 8),Lista).

% e) Que pilotos não pilotam um Edge540? 
% findall((Piloto,Aviao), (pilota(Piloto,Aviao), Aviao \= edge540), Lista).