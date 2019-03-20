oposto(e,d).
oposto(d,e).

%fazendeiro vai sozinho
pode_ir([F, C, L, R], [NF, C, L, R]) :- oposto(F, NF).

%fazendeiro + carneiro vão p/ outra margem
pode_ir([FC, FC, L, R], [NFC, NFC, L, R]) :- oposto(FC, NFC).

%fazendeiro + lobo
pode_ir([FL, C, FL, R], [NFL, C, NFL, R]) :- oposto(FL, NFL).

%fazendeiro + repolho
pode_ir([FR, C, L, FR], [NFR, C, L, NFR]) :- oposto(FR, NFR).

permitido([FC, FC, _, _]).
permitido([X, C, X, X]) :- oposto(C, X).

rota(Ori, Dst, Cam) :- rota(Ori, Dst, [Ori], Cam).
rota(X, X, Cam, Cam).
rota(Ori, Dst, CamAux, Cam) :- pode_ir(Ori, Viz), not(member(Viz, CamAux)),
	permitido(Viz), rota(Viz, Dst, [Viz|CamAux], Cam).


write_rota([]).
write_rota([H|T]) :- write_rota(T), write(H), write(', ').