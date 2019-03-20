%fatos

nota(joao,5.0).
nota(maria,6.0).
nota(joana,8.0).
nota(mariana,9.0).
nota(cleuza,8.5).
nota(jose,6.5).
nota(jaoquim,4.5).
nota(mara,-1).
nota(mary,11).

%relações



%regras

situacaoSlow(Aluno, aprovado) :- nota(Aluno,X), X >= 7, X =< 10.
situacaoSlow(Aluno, recuperacao) :- nota(Aluno,X), X >= 5, X < 7.
situacaoSlow(Aluno, reprovado):- nota(Aluno,X), X >= 0, X < 5.
situacaoSlow(Aluno, erro):- nota(Aluno, X), (X < 0; X > 10).



situacaoFast(Aluno, erro):- nota(Aluno, X), (X < 0; X > 10), !.
situacaoFast(Aluno, reprovado):- nota(Aluno,X), X < 5, !.
situacaoFast(Aluno, recuperacao):- nota(Aluno,X), X < 7, !.
situacaoFast(_, aprovado).