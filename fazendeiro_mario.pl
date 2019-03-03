oposto(o,l).
oposto(l,o).

%fazendeiro vai para a outra margem do rio
pode_ir([F,C,L,R],[NF,C,L,R]):- oposto(F,NF).

%fazendeiro+carneiro vao para a outra margem do rio
pode_ir([FC,FC,L,R],[NFC,NFC,L,R]):- oposto(FC,NFC).

%fazendeiro+lobo vao para a outra margem do rio
pode_ir([FL,C,FL,R],[NFL,C,NFL,R]):- oposto(FL,NFL).

%fazendeiro+repolho vao para a outra margem do rio
pode_ir([FR,C,L,FR],[NFR,C,L,NFR]):- oposto(FR,NFR).

permitido([FC,FC,_,_]). %pode se o fazendeiro está junto do carneiro
permitido([X,C,X,X]):- oposto(X,C). %pode se o carneiro está sozinho

rota(Origem, Destino, Cam):-
	 rota(Origem, Destino, [Origem], Cam).

rota(Destino, Destino, Cam, Cam).

rota(Origem, Destino, CamAux, Cam):-
	 pode_ir(Origem, Vizinha),
	 permitido(Vizinha),
	 not(pertence(Vizinha,CamAux)),
	 rota(Vizinha, Destino, [Vizinha|CamAux], Cam).

pertence(Elem,[Elem|_]):-!.
pertence(Elem,[_|Cauda]):- pertence(Elem, Cauda).

imprime([]).
imprime([C|Cauda]):-imprime(Cauda), nl, write(C).

resolve_fazendeiro(EstIni, EstFinal):- rota(EstIni, EstFinal, Cam), imprime2(Cam).

imprime2([]).
imprime2([_]).
imprime2([C1,C2|Cauda]):- imprime2([C2|Cauda]),
			  nl, mostra(C1,C2).


mostra([F,C,L,R],[NF,C,L,R]):-write('O fazendeiro foi da margem '), ext(F,Texto1),
			write(Texto1), write(' para a margem '), ext(NF,Texto2),
			write(Texto2).


mostra([FC,FC,L,R],[NFC,NFC,L,R]):-write('O fazendeiro levou o carneiro da margem '), ext(FC,Texto1),
			write(Texto1), write(' para a margem '), ext(NFC,Texto2),
			write(Texto2).


mostra([FL,C,FL,R],[NFL,C,NFL,R]):-write('O fazendeiro levou o lobo da margem '), ext(FL,Texto1),
			write(Texto1), write(' para a margem '), ext(NFL,Texto2),
			write(Texto2).

mostra([FR,C,L,FR],[NFR,C,L,NFR]):-write('O fazendeiro levou o repolho da margem '), ext(FR,Texto1),
			write(Texto1), write(' para a margem '), ext(NFR,Texto2),
			write(Texto2).

ext(o,oeste).
ext(l,leste).








