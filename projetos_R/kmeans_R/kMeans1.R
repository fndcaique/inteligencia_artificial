#funcao distancia euclidiana

euc.dist <- function(x1, x2) {
  return (sqrt(sum((x1 - x2) ^ 2)))
}

kMeans1 <- function(dados, k = 2) {
  rotulo = 1:k
  rownames(dados)[nrow(dados)] = 1
  
  #atribuicao aleatoria dos rotulos
  for (i in 1:nrow(dados)) {
    rownames(dados)[i] <-
      sample(rotulo, 1) # sample embaralha o conjunto de dados e o ,1 retorna uma lista com a quantidade de valores que esta na lista
  }
  
  centroids <-
    colMeans(dados[rownames(dados) == 1, ]) # retorna a media por coluna
  
  for (j in 2:k) {
    centroids <-
      rbind(centroids, colMeans(dados[rownames(dados) == j, ])) #junta as linhas dentro das tabelas c bing junta as colunas
  }
  
  #rotulas os centroids
  rownames(centroids) = 1:k
  
  #realizar agrupamentos até que os centroides se estabilizem
  while (TRUE) {
    cent_ant = centroids
    for (i in 1:nrow(dados))
    {
      distancias = NULL
      for (j in 1:k) {
        distancias[j] = euc.dist(dados[i, ], centroids[j, ])# VO retirar a distancias de todas as linhas e todos os centroids
        
      }
      names(distancias) = 1:k
      rownames(dados)[i] = as.numeric(names(distancias[distancias == min(distancias)]))
      
      #recalcular ,os centroids
      
      centroids <- colMeans(dados[rownames(dados) == 1, ])
      
      for (z in 2:k) {
        centroids <-
          rbind(centroids, colMeans(dados[rownames(dados) == z, ]))
        
      }
    }
    ifelse(test = (centroids == cent_ant),
           yes = break,
           no = NA)
  }
  return(list(centroids = centroids, clusters = as.numeric(rownames(dados))))
}

'
dados = as.matrix(iris[, 1:4])
exp = kMeans1(dados, 3)
plot(dados, exp$clusters+1, main="kMeans", pch=20, cex=2)
pairs(dados, exp$clusters+1, main="kMeans", pch=20, cex=2)

browser() #breakpoint para a depuração

'