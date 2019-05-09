library("rpart")
library("rpart.plot")

#dados <- iris
#modelo <- rpart(Species ~ ., data = dados) #colclasses ~ todas
#res <- predict(teste, dados, type = "class") # efetua o teste


cogu <- read.csv(file = "cogumelos.csv", header = TRUE, sep = ",")
seq <- sample(1:nrow(cogu), as.integer(nrow(cogu)*0.3))
seq2 <- sample(1:nrow(cogu), nrow(cogu)-as.integer(nrow(cogu)*0.3))
treino <- dados[seq2, ]
teste <- dados[seq, ]
modelo <- rpart(CLASSES ~ ., data = cogu)
result <- predict(modelo, teste, type = "class")
mconfusao <- table(teste$CLASSES, result)
