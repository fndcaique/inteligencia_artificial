% Fatos

canario(tico).

peixe(nemo).

tubarao(tutu).

vaca(mimosa).

morcego(vamp).

avestruz(xica).

salmao(alfred).


% Relações





% Regras

%todos os animais tem pele
pele(X) :- animal(X).


% peixes, passaros e mamiferos são animais
animal(X) :- peixe(X); passaro(X); mamifero(X).

% peixes tem nadadeiras e podem nadar
peixe(X) :- tem_nadadeira(X), nada(X).

passaro(X) :- tem_asa(X), voa(X).

% peixes e passaros poem ovos, mamiferos não
% tubarões são peixes mas não põem ovos
poem_ovo(X) :- (peixe(X), not(tubarao(X)) ; passaro(X).

% salmão é um tipo de peixe


mamifero(X) :- not(poem_ovo(X)), (anda(X); morcego(X)).

cor(X,amarelo) :- canario(X).

voa(X) :- passaro(X), not(avestruz(X) ; morcego(X).

da_leite(X) :- vaca(X).




