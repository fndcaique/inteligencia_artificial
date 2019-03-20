
pode_ir(prudente, assis, 127).
pode_ir(prudente, marilia, 179).
pode_ir(marilia, assis, 75).
pode_ir(marilia, bauru, 103).
pode_ir(assis, ourinhos, 75).
pode_ir(bauru, ourinhos, 119).
pode_ir(bauru, saocarlos, 155).
pode_ir(ourinhos, saopaulo, 337).
pode_ir(ourinhos, sorocaba, 291).
pode_ir(saocarlos, campinas, 141).
pode_ir(saopaulo, santos, 71).
pode_ir(sorocaba, saopaulo, 100).
pode_ir(campinas, saopaulo, 98).


aresta(A, B, X) :- pode_ir(A, B, X); pode_ir(B, A, X).


rota(Origem, Destino, Caminho, Custo) :- rota(Origem, Destino, [Origem], Caminho, Custo).

rota(Dest, Dest, Cam, Cam, 0).
rota(Ori, Dest, CamAux, Cam, Custo) :- aresta(Ori, Viz, X), not(member(Viz, CamAux)),
	rota(Viz, Dest, [Viz|CamAux], Cam, C2), Custo is C2 + X.

menorcaminho(Ori, Dst, Caminho, Custo) :- findall([Cus, Ori, Dst, Cam], rota(Ori, Dst, Cam, Cus), L),
	sort(L, [Custo, Ori, Dst, Caminho|_]).

menor_dist(Ori, Dst, C, D) :- findall([Custo,Cam], rota(Ori, Dst, Cam, Custo), L) , sort(L, Nl), [C,D]|Nl.
