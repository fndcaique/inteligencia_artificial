% �rsula � bonita. 
% Norberto � rico e bonito. 
% Berta � rica e forte. 
% Pierre � forte e bonito.
% Bruno � am�vel e forte. 
% 
% Todos os homens gostam de mulheres bonitas. 
% Berta gosta de qualquer homem que gosta dela. 
% �rsula gosta de qualquer homem que gosta dela, contando que ele seja rico, am�vel, bonito e forte. 
 
% Qualquer homem que gosta de uma mulher que gosta dele � feliz. 
% Qualquer mulher que gosta de um homem que gosta dela � feliz.
% Todos os homens ricos s�o felizes. 
 
% 

homem(norberto).
homem(pierre).
homem(bruno).

mulher(ursula).
mulher(berta).

bonito(ursula).
bonito(norberto).
bonito(pierre).

rico(norberto).
rico(berta).

forte(berta).
forte(bruno).
forte(pierre).

amavel(bruno).



% Todos os homens gostam de mulheres bonitas. 
gosta(X,Y):-homem(X), mulher(Y), bonito(Y).

% Berta gosta de qualquer homem que gosta dela. 
gosta(berta,Y):- homem(Y), gosta(Y,berta).

% �rsula gosta de qualquer homem que gosta dela, contando que ele seja rico, am�vel, bonito e forte. 
gosta(ursula,Y):- homem(Y),forte(Y),bonito(Y),rico(Y),amavel(Y),gosta(Y,ursula).


% Qualquer homem que gosta de uma mulher que gosta dele � feliz. 
feliz(Pessoa):- homem(Pessoa), mulher(Y), gosta(Pessoa,Y), gosta(Y,Pessoa).
 
% Qualquer mulher que gosta de um homem que gosta dela � feliz.
feliz(Pessoa):- mulher(Pessoa), homem(Y), gosta(Pessoa,Y), gosta(Y,Pessoa).
 
% Todos os homens ricos s�o felizes. 
feliz(Pessoa) :- homem(Pessoa), rico(Pessoa).


%partes do exerc�cio 2
%cor(X,amarelo):- canario(X).

%animais(X):-mamiferos(X);peixes(X);aves(X).

%voa(X):- (aves(X),not(avestruz(X))).
%voa(X):- morcego(X).

%tem_nadadeiras(X):- peixes(X);baleia(X).
%pode_nadar(X):- nadadeiras(X).

%mamiferos(X):-vaca(X);cavalo(X);morcego(X).
%peixes(X):-peixe(X);tubarao(X).

%vaca(mimosa).
%morcego(vamp).
%tubarao(tutu).



%IMC

peso(joao,90).
peso(maria,50).
peso(luis,80).

altura(joao, 1.75).
altura(maria, 1.60).
altura(luis, 1.95).

%imc = peso / altura^2

% IMC	 Classifica��o
% abaixo de 18,5      - Subnutrido ou abaixo do peso
% entre 18,6 e 24,9   - Peso ideal (parab�ns)
% entre 25,0 e 29,9   - Levemente acima do peso
% entre 30,0 e 34,9   - Primeiro grau de obesidade
% entre 35,0 e 39,9   - Segundo grau de obesidade
% acima de 40         - Obesidade m�rbida


imc(Nome,Resultado):- peso(Nome,P), altura(Nome,A), Resultado is P/(A^2),
	resp(Resultado, Mens),nl,write(Mens),nl.
	

resp(Valor,Mensagem):- Valor < 18.5, Mensagem = 'Subnutrido ou abaixo do Peso'.
resp(Valor,Mensagem):- Valor > 18.5, Valor =< 24.9, Mensagem = 'PARAB�NS, Peso Ideal'.






















