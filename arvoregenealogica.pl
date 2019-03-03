%FATOS 
homem(joao).
homem(joaquim).
homem(marcos).
homem(luis).
homem(samuel).
homem(flavio).
homem(caio).

mulher(ana).
mulher(maria).
mulher(luisa).
mulher(sueli).

%RELAÇÕES
progenitor(ana, joaquim).
progenitor(joao,joaquim).
progenitor(joao,luisa).
progenitor(maria,luis).
progenitor(joaquim,luis).
progenitor(luisa,samuel).
progenitor(luisa,sueli).
progenitor(marcos,samuel).
progenitor(marcos,sueli).
progenitor(luis,flavio).
progenitor(marcos, caio).

%REGRAS

avo_(X,Y) :- progenitor(X, Z), progenitor(Z, Y).

bisavo_(X,Y) :- avo_(X,Z), progenitor(Z,Y).

irmao_(X, Y) :- progenitor(Z, X), progenitor(Z, Y), X \= Y.

tio_(X, Y) :- progenitor(Z, Y), irmao_(X, Z).

primo_(A,B) :- progenitor(X,A), tio_(X,B).

pai(X,Y) :- progenitor(X,Y), homem(X).
mãe(X,Y) :- progenitor(X,Y), mulher(X).

avó(X,Y) :- avo_(X,Y), mulher(X).
avô(X,Y) :- avo_(X,Y), homem(X).

bisavô(X,Y) :- homem(X), bisavo_(X,Y).
bisavó(X,Y) :- mulher(X), bisavo_(X,Y).

irmão(X,Y) :- irmao_(X,Y), homem(X).
irmã(X,Y) :- irmao_(X,Y), mulher(X).

primo(X,Y) :- primo_(X,Y), homem(X).
prima(X,Y) :- primo_(X,Y), mulher(X).


%countXinList(X,[],0).
%countXinList(X,[X|T],C) :- !,countXinList(X,T,C1), C is C1+1.
%countXinList(X,[Y|T],C) :- countXinList(X,T,C).

conta_irmaos(X,N) :- setof([X,Y], (irmao_(X,Y)), L),length(L,N).


% findall(filtro, condicao, varresult)
% findall([X,Y], (irmao_(X,Y),X@<Y),Lista)


% setof(filtro, condicao, varresult)
% setof([X,Y], (irmao_(X,Y),X@<Y),Lista)
