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

## Considerações sobre a implementação

Seguindo as especificações, foram criados 4 endpoints:

### Endpoint POST para adicionar produtos da Wishlist do cliente

---

Nesse endpoint, deve ser enviado o `clientId` e o `produtoIds` no seguinte formato:

```json
{
  "clienteId": 1,
  "produtoIds": [1]
}
```
Regras:

- O campo `clientId` não pode ser vazio e deve ser do tipo Long;
- O campo `produtoIds` não pode ser vazio e deve ser uma lista do tipo Long;
- O campo `produtoIds` não pode conter mais de 20 produtos;
- Caso não exista uma Wishlist criada com o mesmo `clientId`, será criado um novo documento e a lista `produtoIds` será adicionada;
- Caso exista Wishlist criada com o mesmo `clientId`, será verificado se não está sendo adicionado o mesmo produto:
  - Se sim, retorna uma exception informando que já existe o produto na Wishlist do cliente;
  - Se não, os produtos são adicionados na Wishlist existente;
  - Antes de salvar no banco, é feita novamente a validação para ver se o limite máximo de 20 produtos não será excedido.
  


### Endpoint GET para obter todos os produtos da Wishlist do cliente

Nesse endpoint deve ser enviado o `clientId` como `PathVariable`.

Regras:

- O campo `clientId` não pode ser vazio e deve ser do tipo Long;
- Será feita uma busca para saber se existe uma Wishlist cadastrada com esse `clientId`:
  - Se sim, será retornada a Wishlist do cliente contendo todos os produtos cadastrados;
  - Se não, será retornada uma exception informando que não existe Wishlist para esse `clientId`.

### Endpoint GET para obter um produto da Wishlist do cliente

Nesse endpoint deve ser enviado o `clientId` como `PathVariable` e um `produtoId` como `QueryParam`.

Regras:

- O campo `clientId` não pode ser vazio e deve ser do tipo Long;
- O campo `produtoId` não pode ser vazio e deve ser do tipo Long;
- Será feita uma busca para saber se existe na Wishlist com o `clientId` informado, um produto com o mesmo `produtoId` informado:
  - Se sim, retorna o `produtoId` encontrado;
  - Se não, retorna uma exception informando que não foi encontrado um produto com o `clientId` e `produtoId` informados.

### Endpoint DELETE para remover um produto da Wishlist do cliente

Nesse endpoint deve ser enviado o `clientId` como `PathVariable`.

- O campo `clientId` não pode ser vazio e deve ser do tipo Long;
- O campo `produtoId` não pode ser vazio e deve ser do tipo Long;
- Será feita uma busca para saber se existe na Wishlist com o `clientId` informado, um produto com o mesmo `produtoId` informado:
  - Se sim, o produto será deletado da lista de `produtoIds` da Wishlist do cliente;
  - Se não, retorna uma exception informando que não foi encontrado um produto com o `clientId` e `produtoId` informados.