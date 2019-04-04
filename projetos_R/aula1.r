#fun??o dist?ncia euclidiana

euc.dist <- function(x1, x2) {
  return (sqrt(sum((x1 - x2) ^ 2)))
}


'
gerar centroids aleatórios
atribuir dados aos centroids aleatoriamente

faca{
  
}equanto nao houver mudanca nos centroids

'

kMeans1 <- function(dados, k = 2) {
  #r?tulos
  rotulo = 1:k
  rownames(dados)[nrow(dados)] = 1
  
  # random centroids
  for (i in 1:nrow(dados)) {
    rownames(dados)[i] <- sample(rotulo, 1)
  }
  
  
  centroids <- colMeans(dados[rownames(dados) == 1, ])
  
  for (j in 2:k) {
    centroids <-
      rbind(centroids, colMeans(dados[rownames(dados) == j, ]))
  }
  
  #realizar agrupamentos até que os centroides se estabilizem
  while (TRUE) {
    cent_ant = centroids
    for (i in 1:nrow(dados)) {
      distancias = NULL
      for (j in 1:k) {
        distancias[j] = euc.dist(dados[i, ], centroids[j, ])
      }
      
      names(distancias) = 1:k
      #print(distancias)
      
      # rotula??o
      rownames(dados)[i] = as.numeric(names(distancias[distancias == min(distancias)]))
      
      # recalcular centroides
      
      centroids <- colMeans(dados[rownames(dados) == 1, ])
      
      for (z in 2:k) {
        centroids <-
          rbind(centroids, colMeans(dados[rownames(dados) == z, ]))
      }
    }
    
    ifelse(test = (cent_ant == centroids),
           yes = break,
           no = NA)
  }
  return (list(centroids = centroids, clusters = as.numeric(rownames(dados))))
  
}


'
dados <- as.matrix(iris[,1:4])
exemplo = kMeans1(dados,k=3)
plot(dados,col = exemplo$clusters+1, main="kMeans", pch=20, cex=2)
browser()
pairs(dados, col=exemplo$clusters+1)
'
