# API MongoDB - Documentação

A API MongoDB é uma aplicação que permite gerenciar um cardápio, armazenando informações no banco de dados MongoDB. A API fornece endpoints para criar, ler, atualizar e excluir comidas, bem como fazer upload de imagens relacionadas a essas comidas.

## Recurso "Comidas"

### [GET] /food/retornatudo

Obtém a lista de todos as comidas cadastradas no cardápio, incluindo informações sobre as imagens associadas a cada comida.

**Exemplo de resposta:**

```json
[
  {
    "name": "Food 1",
    "descriptor": "Descriptor 1",
    "price": 10.0,
    "type": "image/jpeg",
    "data": "File 1 content"
  },
  {
     "name": "Food 2",
    "descriptor": "Descriptor 2",
    "price": 20.0,
    "type": "image/png",
    "data": "File 2 content"
  }
]
 ```
### [POST] /food
Cria uma nova comida no cardápio.

Parâmetros de entrada:

```json
{
  "name": "Food 1",
  "descriptor": "Descriptor 1",
  "price": 10.0
} 
```

Exemplo de resposta:

```json

{
  "id": "id mongoDb",
  "name": "Food 1",
  "descriptor": "Descriptor 1",
  "price": 10.0
}
```

### [GET] /food/{id}
Obtém as informações de uma comida específica com base no seu ID.

Exemplo de resposta (status 200):


```json
{
  "id": "1",
  "name": "Food 1",
  "descriptor": "Descriptor 1",
  "price": 10.0
}
 ```
### [PUT] /food/{id}
Atualiza as informações de uma comida específica com base no seu ID.

Parâmetros de entrada:

```json
{
  "name": "New Food 1",
  "descriptor": "New Descriptor 1",
  "price": 15.0
} 
```
Exemplo de resposta (status 200):

```json
{
  "id": "id mongoDB",
  "name": "New Food 1",
  "descriptor": "New Descriptor 1",
  "price": 15.0
}
```
## Recurso "Imagens"
### [POST] /food/foto/{id}
Faz o upload de uma imagem para uma comida específico com base no seu ID.

Parâmetros de entrada:

- file: Arquivo de imagem a ser enviado.

Exemplo de resposta:

```json
{
  "id": "1",
  "nameFile": "image.jpg",
  "type": "image/jpeg",
  "data": "Image content"
}
``` 
### [GET] /food/foto/{id}
Obtém a imagem associada a uma comida específica com base no seu ID.

Exemplo de resposta (status 200):

[imagem.data]

### [PUT] /food/foto/{id}
Atualiza a imagem associada a uma comida específica com base no seu ID.

Parâmetros de entrada:

- file: Arquivo de imagem a ser enviado.



 
