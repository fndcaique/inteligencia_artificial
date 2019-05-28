#função de ativação - logística
funcao.ativacao <- function(v){
  y <- 1 / (1 + exp(-v))

  return(y)
}

#Derivada da função - logística
der.funcao.ativacao <- function(y){
   derivada <- y * (1-y)
   return(derivada)
}

#Arquitetura da Rede MLP
arquitetura <- function(num.entradas, num.escondidas, num.saida,
                        funcao.ativacao, der.funcao.ativacao){
  
  arq <- list()
  
  #Parametros da rede
  arq$num.entradas <- num.entradas
  arq$num.escondidas <- num.escondidas
  arq$num.saida <- num.saida
  arq$funcao.ativacao <- funcao.ativacao
  arq$der.funcao.ativacao <- der.funcao.ativacao
  
  
  #2 neuronios na camada escondida
  #
  #       Ent1  Ent2  Bias
  #
  #  1    w11    w12   w13  
  #  2    w21    w22   w23
  
  
  #Pesos conectando entrada e camada escondida
  num.pesos.entrada.escondida <- (num.entradas+1)*num.escondidas
  arq$escondida <- matrix(runif(min=-0.5,max=0.5, num.pesos.entrada.escondida),
                          nrow=num.escondidas, ncol=num.entradas+1)
  
  #Pesos conectando a camada escondida e a camada de saída
  num.pesos.escondida.saida <- (num.escondidas+1)*num.saida
  arq$saida <- matrix(runif(min=-0.5, max=0.5, num.pesos.escondida.saida),
                      nrow=num.saida, ncol=num.escondidas+1)
  
  return(arq)
  
}

mlp.propagacao <- function(arq, exemplo){
  
  # Entrada -> Camada escondida
  
  net.entrada.escondida <- arq$escondida %*% as.numeric(c(exemplo,1))
  i.entrada.escondida <- arq$funcao.ativacao(net.entrada.escondida)
  
  # Camada escondida -> saída
  net.escondida.saida <- arq$saida %*% c(i.entrada.escondida,1)
  i.escondida.saida <- arq$funcao.ativacao(net.escondida.saida)
  
  #Resultados
  
  resultado <- list()
  resultado$net.entrada.escondida <- net.entrada.escondida
  resultado$i.entrada.escondida <- i.entrada.escondida
  resultado$net.escondida.saida <- net.escondida.saida
  resultado$i.escondida.saida <- i.escondida.saida
  
  return(resultado)
  
}


mlp.retropropagacao <- function(arq, treino, n, limiar, rotulos.classes,max.epocas=2000){
  
  erroQuadratico <- 2*limiar
  epocas <- 0
  
  #Treinar enquanto o erroquadrático forma mior que um limiar e qtd de épocas 
  #for menor que 2000
  
  
  desejado <- diag(1,arq$num.saida)
  rownames(desejado)<- rotulos.classes
  
  
  while (erroQuadratico > limiar && epocas <= max.epocas){
     erroQuadratico <- 0
     
     dados <- treino[sample(1:nrow(treino),nrow(treino)),]
     
     #Treino para todos os exemplos
     for (i in 1:nrow(dados)){
       
       #Separar um exemplo do conjunto
       x.entrada <- dados[i,1:arq$num.entrada]
       x.saida <- dados[i,ncol(dados)]

       # Verificar a saída da rede para o exemplo separado
       resultado <- mlp.propagacao(arq,x.entrada)
       iSaida <- resultado$i.escondida.saida
       
       #Calcula erro da saída (****** 1 neurônio ******)
       erro <- desejado[x.saida,] - iSaida
      # browser() 
       #Soma do erro quadrático
       erroQuadratico <- erroQuadratico + 0.5*sum(erro^2) #*** ajustar com Erro da Rede = 1/2 * sum(erro^2) )
       
       # Gradiente do Erro da Saída
       grad.erro.saida <- erro * arq$der.funcao.ativacao(iSaida)
       
       # Gradiente do Erro para Camada Escondida
       pesos.saida <- arq$saida[,1:arq$num.escondida]
       
       grad.erro.escondida <- as.numeric(arq$der.funcao.ativacao(resultado$i.entrada.escondida))*
         (t(grad.erro.saida) %*% pesos.saida)
       
       #Ajuste pesos 
       #Saida
       arq$saida <- arq$saida + n * (grad.erro.saida %*% 
                                     c(resultado$i.entrada.escondida,1))
       
       #Escondida
       arq$escondida <- arq$escondida + n * (t(grad.erro.escondida) %*% 
                                             as.numeric(c(x.entrada, 1)))
             
     } 
     
     erroQuadratico <- erroQuadratico/nrow(dados)
     cat("\nEpoca", epocas," - Erro Médio = ", erroQuadratico)
     epocas <- epocas + 1
  }
  
  retorno <- list()
  retorno$arq <- arq
  retorno$epocas <- epocas
  retorno$desejado <- desejado
  
  return(retorno)
}


