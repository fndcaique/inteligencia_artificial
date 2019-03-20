
pode_ir(a,b).
pode_ir(a,c).
pode_ir(b,d).
pode_ir(c,d).
pode_ir(c,e).
pode_ir(d,g).
pode_ir(e,f).
pode_ir(e,g).

rota(Origem, Destino, Cam) :- rota(Origem, Destino, [Origem], Cam). % busca em profundidade
rota(Destino, Destino, Cam, Cam). % faz copia do caminho para não perdelo no desempilhamento
rota(Origem, Destino, CamAux, Cam) :- aresta(Origem, Vizinho), not(member(Vizinho, CamAux)),
		rota(Vizinho, Destino, [Vizinho|CamAux], Cam).

aresta(A, B) :- pode_ir(A, B); pode_ir(B, A). % transforma o digrafo em um grafo

aresta(A, B, C) :- pode_ir(A, B, C)

writeRota([]).
writeRota([H|T]) :- writeRota(T), write(H), write(', ').