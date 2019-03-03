d(0).
d(1).

num([A,B,C]) :- d(A), d(B), d(C), !.


f(X,0) :- X < 3.
f(X,1) :- X < 6.
f(X,2).


 write(Algo).  escreve a variavel na tela.
 nl().  pula linha.
 sort(L,NL).  NL recebe L ordenada.
 !.  corte.




 1a regra: atomos.
	joao = joao.  true.
	10 = 10. true.
	10 = dez. false.

2a regra: variaveis.
	X = 1, X is X+1. false.
	X = 1, NX is X+1. true. NX = 2.

3a regra: estruturas.
	functor(termos).
	pessoa(joao,datanasc(10,10,1995),analista).


corte verde:
	melhora a execução do programa, executando somente o que é preciso. if ... else.
	f(X,0) :- X < 3, !.
	f(X,1) :- X >= 3, X < 6, !.
	f(X,2) :- X >= 6.

corte vermelho:
	melhora a execução e retira escrita desnecessária, mas se torna obrigatório.
	f(X,0) :- X < 3, !.
	f(X,1) :- X < 6, !.
	f(X,2).
	

 Busca em Profundidade e Busca em Largura (Buscas Cegas)
 
 B.P. Não Adimissível, Não Ótima.
 B.L. Adimissível, Ótima.
