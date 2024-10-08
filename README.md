# Desafio Luizalabs

Projeto realizado para o desafio técnico da LuizaLabs onde era necessário criar uma Wishlist de produtos por cliente.

## Tecnologias do projeto

- **Linguagem:** Java 21
- **Framework http:** Spring Boot 3.3
- **Gerenciador de dependência:** Gradle 8.10
- **Banco de dados:** MongoDB 3.3

## Executando a Aplicação

A aplicação está configurada para rodar usando apenas Docker sem a necessidade de ter as dependências instaladas localmente.

Existem dois Dockerfiles no projeto:

- Um para fazer o build e rodar a aplicação
- E outro para rodar os testes e expor o relatório de coverage do Jacoco

A organização dos containers foi toda feita no arquivo `docker-compose.yml` onde são criados os containers para cada Dockerfile e também o container do banco de dados.

Para rodar o projeto, basta executar o comando abaixo:

```bash
docker compose up
```

Pode ser executado também com a flag `-d` no final do comando para executar todos os containers em segundo plano.

```bash
docker compose up -d
```

Após a execução do comando, a aplicação ficará disponível na porta `8080` e poderá ser acessada através do Swagger:

- http://localhost:8080/swagger-ui/index.html

O relatório de coverage do Jacoco ficará disponível na porta `8081`:

- http://localhost:8081

Caso seja necessário acessar o MongoDB para verificar as alterações, isso pode ser feito com a URI de conexão abaixo:

- `mongodb://localhost:27017/`

Durante o desenvolvimento da aplicação, foi utilizada a ferramenta MongoDB Compass.

## Considerações sobre a implementação

Seguindo as especificações, foram criados 4 endpoints:

### Endpoint POST para adicionar produtos da Wishlist do cliente

Nesse endpoint, deve ser enviado o `clienteId` e o `produtoIds` no seguinte formato:

```json
{
  "clienteId": 1,
  "produtoIds": [1]
}
```
Regras:

- O campo `clienteId` não pode ser vazio e deve ser um número inteiro;
- O campo `produtoIds` não pode ser vazio e deve ser uma lista de números inteiros;
- O campo `produtoIds` não pode conter mais de 20 produtos;
- Caso não exista uma Wishlist criada com o mesmo `clienteId`, será criado um novo documento e a lista `produtoIds` será adicionada;
- Caso exista uma Wishlist criada com o mesmo `clienteId`, será verificado se não está sendo adicionado o mesmo produto:
  - Se sim, retorna uma exception informando que já existe o produto na Wishlist do cliente;
  - Se não, os produtos são adicionados na Wishlist existente;
  - Antes de salvar no banco, é feita novamente a validação para ver se o limite máximo de 20 produtos não será excedido.
  


### Endpoint GET para obter todos os produtos da Wishlist do cliente

Nesse endpoint deve ser enviado o `clienteId` como `PathVariable`.

Regras:

- O campo `clienteId` não pode ser vazio e deve ser um número inteiro;
- Será feita uma busca para saber se existe uma Wishlist cadastrada com esse `clienteId`:
  - Se sim, será retornada a Wishlist do cliente contendo todos os produtos cadastrados;
  - Se não, será retornada uma exception informando que não existe Wishlist para esse `clienteId`.

### Endpoint GET para obter um produto da Wishlist do cliente

Nesse endpoint deve ser enviado o `clienteId` como `PathVariable` e um `produtoId` como `QueryParam`.

Regras:

- O campo `clienteId` não pode ser vazio e deve ser um número inteiro;
- O campo `produtoId` não pode ser vazio e deve ser um número inteiro;
- Será feita uma busca para saber se o `produtoId` existe na Wishlist do `clienteId` informado:
  - Se sim, retorna o `produtoId` encontrado;
  - Se não, retorna uma exception informando que não foi encontrado um produto para os parametros informados.

### Endpoint DELETE para remover um produto da Wishlist do cliente

Nesse endpoint deve ser enviado o `clienteId` como `PathVariable` e um `produtoId` como `QueryParam`.

Regras:

- O campo `clienteId` não pode ser vazio e deve ser um número inteiro;
- O campo `produtoId` não pode ser vazio e deve ser um número inteiro;
- Será feita uma busca para saber se o `produtoId` existe na Wishlist do `clienteId` informado:
  - Se sim, o produto será deletado da lista de `produtoIds` da Wishlist do cliente;
  - Se não, retorna uma exception informando que não foi encontrado um produto com o `clienteId` e `produtoId` informados.