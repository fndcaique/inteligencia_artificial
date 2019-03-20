
% 19-02-2019 - LISTA

% 1o : Critério Base (critério de parada) % deve vir primeiro na escrita
pertence(Elem, [Elem|_]).
% 2o : Chamada Recursiva
pertence(Elem, [C| Cauda]) :- pertence(Elem,Cauda).


ultimo(Elem, [Elem]) :- !.
ultimo(Elem, [C| Cauda]) :- ultimo(Elem, Cauda).

tamanho([],0).
tamanho([_|Cauda],T) :- tamanho(Cauda,Aux), T is Aux + 1.


contar(0, _, []).
contar(Qtde, Elem, [Elem |Cauda]) :- contar(Q, Elem, Cauda), Qtde is Q + 1, !.
contar(Qtde, Elem, [X |Cauda]) :- contar(Qtde, Elem, Cauda).


soma([],0) :- !.
soma([C,Cauda],S) :- soma(Cauda, S1), S is S1 + C.


maior([X],X) :- !.
maior([H | T], Maior) :- maior(T, Aux), ((H > Aux, Maior = H, !); Maior = Aux).


busca([X|Cauda], 0, X) :- !.
busca([C|Cauda], Pos, X) :- busca(Cauda, Aux, X), Pos is Aux + 1.


remove([Elem|Cauda], Elem, Cauda).
remove([C|Cauda], Elem, Lista) :- remove(Cauda, Elem, Aux), Lista = [C|Aux].

removeAll([], _, []).
removeAll([Elem|Cauda], Elem, Lista) :- removeAll(Cauda, Elem, Lista), !.
removeAll([C|Cauda], Elem, Lista) :- removeAll(Cauda, Elem, Nlista), Lista = [C|Nlista].

insere(Lista, Elem, Nlista) :- remove(Nlista, Elem, Lista).