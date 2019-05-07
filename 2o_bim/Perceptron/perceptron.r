perceptron.treino <- function(dados,
                              pesos,
                              n = 0.3,
                              limite = 100) {
  #n : taxa de aprendizado
  
  #controle
  nepocas <- 0
  errogeral <- 1
  
  while (errogeral != 0 && nepocas < limite) {
    errogeral <- 0
    nepocas <- nepocas + 1
    
    for (i in 1:nrow(dados)) {
      # 1 até a quantidade de linhas dos dados
      net <- 0
      for (j in 1:length(pesos)) {
        net <- net + dados[i, j] * pesos[j]
      }
      
      #funcao sinal, se net < 0 y = -1, senao y = 1
      if (net > 0) {
        y <- 1
      } else{
        y <- -1
      }
      
      
      #atualização dos pesos
      desejado <- length(dados[i, ])
      erro <- dados[i, desejado] - y
      
      if (erro != 0) {
        errogeral <- errogeral + erro ^ 2 #gradiente descendente
        for (j in 1:length(pesos)) {
          pesos[j] <- pesos[j] + n * erro * dados[i, j]
        }
      }
      
    }#for nrow(dados)
    erromedio <- errogeral + erro ^ 2
    cat("erro medio = ", erromedio, "\n")
    
  }#while(errogeral != 0)
  
  cat("finalizado com ", nepocas, " epocas\n")
  
}#perceptron.treino

'
ANOTAÇÕES
#dataframe uni colunas
dados <- data.frame(c1, c2, c3, ..., cn)

pesos <- runif(qtdevalores, limiteinferior, limitesuperior)

cores <- dados$classe
cores[cores==-1] <- 2
plot(x1, x2, col=cores, pch=ifelse(dados$classe > 0, "+", "-"), cex = 2, lwd = 2)
intercep <- - novo.peso[3]/novo.peso[2]
incl <- - novo.peso[1]/novo.peso[2]
abline(intercep, incl, col="green")




sites para bases de dados (uci dataset, )
'