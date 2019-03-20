oposto(e,d).
oposto(d,e).

%1missionário
pode_ir([ME, CE, MD, CD, B], [NME, CE, NMD, CD, NB]) :- oposto(B, NB),
	((B = e, ME > 0, NME is ME - 1, NMD is MD + 1);
			MD > 0, NMD is MD - 1, NME is ME + 1).

%1canibal
pode_ir([ME, CE, MD, CD, B], [ME, NCE, MD, NCD, NB]) :- oposto(B, NB),
	((B = e, CE > 0, NCE is CE - 1, NCD is CD + 1);
	CD > 0, NCD is CD - 1, NCE is CE + 1).

%1canibal + 1missionário
pode_ir([ME, CE, MD, CD, B], [NME, NCE, NMD, NCD, NB]) :- oposto(B, NB),
	((B = e, CE > 0, ME > 0, NME is ME - 1, NMD is MD + 1, NCE is CE - 1, NCD is CD + 1);
		MD > 0, NMD is MD - 1, NME is ME + 1, CD > 0, NCD is CD - 1, NCE is CE + 1).

%2missionários
pode_ir([ME, CE, MD, CD, B], [NME, CE, NMD, CD, NB]) :- oposto(B, NB),
	((B = e, ME > 1, NME is ME - 2, NMD is MD + 2);
			MD > 1, NMD is MD - 2, NME is ME + 2).

%2canibais
pode_ir([ME, CE, MD, CD, B], [ME, NCE, MD, NCD, NB]) :- oposto(B, NB),
	((B = e, CE > 1, NCE is CE - 2, NCD is CD + 2);
	CD > 1, NCD is CD - 2, NCE is CE + 2).

permitido([ME, CE, MD, CD, _]) :- ME >= CE, MD >= CD.
permitido([0, _, _, _, _]).
permitido([_, _, 0, _, _]).

rota(Ori, Dst, Cam) :- rota(Ori, Dst, [Ori], Cam).
rota(X, X, Cam, Cam).
rota(Ori, Dst, CamAux, Cam) :- pode_ir(Ori, Viz), not(member(Viz, CamAux)),
	permitido(Viz), rota(Viz, Dst, [Viz|CamAux], Cam).


write_rota([]).
write_rota([H|T]) :- write_rota(T), write(H), write(', ').