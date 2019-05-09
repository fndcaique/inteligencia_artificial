#funcao de ativacao - logistica
funcao.ativacao <- function(v) {
  y <- 1 / (1 + exp(-v))
  return (y)
}

#derivada da funcao - logistica
der.funcao.ativacao <- function(y) {
  derivada <- y * (1 - y)
  return (derivada)
}

#arquitetura da rede MLP
arquitetura <-
  function(num.entradas,
           num.escondidas,
           num.saida,
           funcao.ativacao,
           der.funcao.ativacao) {
    arq <- list()
    
    #parametros da rede
    arq$num.entradas <- num.entradas
    arq$num.escondidas <- num.escondidas
    arq$num.saida <- num.saida
    arq$funcao.ativacao <- funcao.ativacao
    arq$der.funcao.ativacao <- der.funcao.ativacao
    
    
    
    #pesos conectando entrada e camada escondida
    num.pesos.entrada.escondida <-
      (num.entradas + 1) * num.escondidas
    arq$escondida <-
      matrix(
        runif(
          min = -0.5,
          max = 0.5,
          num.pesos.entrada.escondida
        ),
        nrow = num.escondidas,
        ncol = num.entradas + 1
      )
    
    #pesos conectando a camada escondida e a camada de saida
    num.pesos.escondida.saida <- (num.escondidas + 1) * num.saida
    arq$saida <-
      matrix(
        runif(
          min = -0.5,
          max = 0.5,
          num.pesos.escondida.saida
        ),
        nrow = num.saida,
        ncol = num.escondidas + 1
      )
    
    return(arq)
  }

mlp.propagacao <- function(arq, exemplo) {
  #entrada para a camada escondida
  net.entrada.escondida <-
    arq$escondida %*% as.numeric(c(exemplo, 1))
  i.entrada.escondida <- arq$funcao.ativacao(net.entrada.escondida)
  
  # camada escondida para a saida
  net.escondida.saida <- arq$saida %*% c(i.entrada.escondida, 1)
  i.escondida.saida <- arq$funcao.ativacao(net.escondida.saida)
  
  #resultado
  resultado <- list()
  resultado$net.entrada.escondida <- net.entrada.escondida
  resultado$i.entrada.escondida <- i.entrada.escondida
  resultado$net.escondida.saida <- net.escondida.saida
  resultado$i.escondida.saida <- i.escondida.saida
  
  return(resultado)
}

mlp.retropropagacao <-
  function(arq, treino, n, limiar, max.epocas = 2000) {
    erroQuadratico <- 2 * limiar
    epocas <- 0
    
    #treinar enquanto o erroQuadratico for maior que um limiar e qtde de epocas for menor que 2000
    
    while (erroQuadratico > limiar && epocas <= max.epocas) {
      erroQuadratico <- 0
      
      #embaralhar as linhas para não viciar a rede
      dados <- treino[sample(1:nrow(treino), nrow(treino)), ]
      #treino para todos os exemplos
      for (i in 1:nrow(dados)) {
        #separar um exemplo do conjunto
        x.entrada <- dados[i, 1:arq$num.entrada]
        x.saida <- dados[i, ncol(dados)]
        
        #verificar a saida da rede para o exemplo separado
        resultado <- mlp.propagacao(arq, x.entrada)
        iSaida <- resultado$i.escondida.saida
        
        #calcular erro da saida (******** 1 neuronio ********)
        erro <- x.saida - iSaida
        
        #soma do erro quadratico
        erroQuadratico <-
          erroQuadratico + erro ^ 2 #(*** ajustar com erro da rede = 1/2 * sum(erro^2))
        
        
        
        #gradiente do erro da saida
        grad.erro.saida <- erro * arq$der.funcao.ativacao(iSaida)
        
        #gradiente do erro para a camada escondida
        pesos.saida <- arq$saida[, 1:arq$num.escondida]
        grad.erro.escondida <-
          as.numeric(arq$der.funcao.ativacao(resultado$i.entrada.escondida)) * (grad.erro.saida %*% pesos.saida)
        
        #ajuste pesos
        #saida
        arq$saida <-
          arq$saida + n * (grad.erro.saida %*% c(resultado$i.entrada.escondida, 1))
        
        #escondida
        arq$escondida <-
          arq$escondida + n * (t(grad.erro.escondida) %*% as.numeric(c(x.entrada, 1)))
        
      }
      
      erroQuadratico <- erroQuadratico / nrow(dados)
      cat("Epoca ", epocas, " Erro Médio = ", erroQuadratico, "\n")
      epocas <- epocas + 1
    }#laço erro quadratico e max epocas
    
    retorno <- list()
    retorno$arq <- arq
    retorno$epocas <- epocas
    
    return(retorno)
    
  }

go <- function(value) {
  cont <- 0
  while (TRUE) {
    ifelse(test = value >= 0, break, NA)
    value <- value + 1
    cont <- cont + 1
  }
  yes = cat("yes ",cont, "\n")
}
