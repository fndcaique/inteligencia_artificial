%FATOS
homem(norberto).
homem(pierre).
homem(bruno).

mulher(ursula).
mulher(berta).

bonito(ursula).
bonito(norberto).
bonito(pierre).

forte(berta).
forte(pierre).
forte(bruno).

rico(norberto).
rico(berta).

amavel(bruno).


%RELAÇÕES



%REGRAS


gosta(X, Y) :- homem(X), mulher(Y), bonito(Y).
gosta(berta, X) :- homem(X), gosta(X,berta).
gosta(ursula, X) :- homem(X), gosta(X,ursula), rico(X), amavel(X), bonito(X), forte(X).

feliz(X) :- homem(X), rico(X).
feliz(X) :- homem(X), gosta(X,Y), gosta(Y,X).
feliz(X) :- mulher(X), gosta(X,Y), gosta(Y,X).




%