% Fatos

canario(tico).

peixe(nemo).
% salmão é um tipo de peixe
peixe(X) :- salmao(X); tubarao(X).

tubarao(tutu).

vaca(mimosa).

morcego(vamp).

avestruz(xica).

salmao(alfred).


% Relações



% Regras
delicia(X) :- salmao(X).
%todos os animais tem pele
tem_pele(X) :- animal(X).


% peixes, passaros e mamiferos são animais
animal(X) :- peixe(X); ave(X); mamifero(X).

% peixes tem nadadeiras e podem nadar +

tem_nadadeira(X) :- peixe(X).

pode_nadar(X) :- tem_nadadeira(X).

pode_voar(X) :- tem_asa(X), not(avestruz(X)).

tem_asa(X) :- ave(X) ; morcego(X).

% peixes e passaros poem ovos, mamiferos não
% tubarões são peixes mas não põem ovos
poem_ovo(X) :- (peixe(X), not(tubarao(X))) ; ave(X).


cor(X, amarelo) :- canario(X).


da_leite(X) :- vaca(X).

comida(X):- vaca(X).

ave(X) :- canario(X); avestruz(X); tem_asa(X), poem_ovo(X).

mamifero(X) :- vaca(X); morcego(X); not(poem_ovo(X)), anda(X).





