# Cadastro de Produtos API

API REST para cadastro de produtos, evolução de um script de automação por cliques de tela. Projeto em construção, começando por um CRUD básico.

## Descrição

API REST desenvolvida para realizar o cadastro, consulta, atualização e remoção de produtos. O projeto substitui gradualmente um processo de automação por cliques de tela por uma API estruturada.

## Stack

* Java
* Spring Boot
* Spring Data JPA
* H2 (provisório)

## Como rodar

```bash
.\mvnw spring-boot:run
```

O console do banco fica disponível em:

`http://localhost:8080/h2-console`

**Configuração do H2:**

* JDBC URL: `jdbc:h2:mem:produtosdb`
* Usuário: `sa`
* Senha: vazia

## Endpoints

| Método   | Endpoint         | Sucesso          | Erros                                              |
| -------- | ---------------- | ---------------- | -------------------------------------------------- |
| `GET`    | `/produtos`      | `200 OK`         | —                                                  |
| `GET`    | `/produtos/{id}` | `200 OK`         | `404 Not Found`                                    |
| `POST`   | `/produtos`      | `201 Created`    | `400 Bad Request`, `409 Conflict`                  |
| `PUT`    | `/produtos/{id}` | `200 OK`         | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| `DELETE` | `/produtos/{id}` | `204 No Content` | `404 Not Found`                                    |

## Decisões técnicas

### `BigDecimal` em vez de `double`

`BigDecimal` foi utilizado para representar valores monetários porque o `double` utiliza representação binária e pode gerar imprecisões em operações com valores decimais.

Por exemplo:

```text
0.1 + 0.2 = 0.30000000000000004
```

Com `BigDecimal`, o valor pode ser representado com precisão decimal. No banco, o campo utiliza 2 casas decimais (`precision = 12, scale = 2`).

### `codigo` e `tipo` como `String`

Os campos `codigo` e `tipo` foram definidos como `String` porque o código pode conter letras ou zeros à esquerda.

Por exemplo:

```text
001
A001
PROD-001
```

Se o código fosse armazenado como um tipo numérico, informações como os zeros à esquerda seriam perdidas.

### Construtor sem argumentos na entidade

A entidade possui um construtor sem argumentos porque o Hibernate precisa conseguir criar uma instância vazia da entidade e preencher seus atributos posteriormente ao ler os dados do banco.

O construtor é `protected` para evitar que outras partes da aplicação criem produtos vazios diretamente.

### Tratamento de erros — Ticket #10

**Por que `404` e `409` em vez de `500`?**

Porque `404` representa que o recurso solicitado não foi encontrado e `409` representa um conflito, como tentar cadastrar um produto com código que já existe. O `500` deve representar um erro inesperado no servidor.

**Por que o tratamento fica em uma classe só e não em `try/catch` em cada método do controller?**

Porque uma classe global centraliza o tratamento das exceções e evita repetir `try/catch` em todos os métodos do controller, deixando o código mais organizado e fácil de manter.

**O que acontece para quem consome a API se todo erro virar `500`?**

O consumidor não consegue saber corretamente o que aconteceu. Um `500` pode indicar tanto um recurso inexistente quanto um conflito ou um erro interno, dificultando o tratamento do problema pelo cliente.

## Anotações da entidade

* `@Entity`: marca a classe como uma entidade JPA mapeada para uma tabela.
* `@Id`: define a chave primária da entidade.
* `@GeneratedValue`: permite que o valor da chave primária seja gerado automaticamente.
* `unique = true`: impede que dois produtos tenham o mesmo `codigo`. Quando ocorre uma duplicidade, o banco gera um erro de integridade, que o Spring converte em `DataIntegrityViolationException`.

## Testes manuais

| Cenário                      | Antes do Dia 10             | Depois do Dia 10  |
| ---------------------------- | --------------------------- | ----------------- |
| `GET /produtos/999`          | `500 Internal Server Error` | `404 Not Found`   |
| `POST` com `codigo` repetido | `500 Internal Server Error` | `409 Conflict`    |
| `POST` sem `marca`           | `500 Internal Server Error` | `400 Bad Request` |

Os testes mostram a evolução do tratamento de erros da API, deixando de retornar `500` para situações esperadas e passando a utilizar códigos HTTP específicos para cada situação.

## Próximos passos

* Criar testes unitários para o `Service`.
* Criar testes para o `Controller`.
* Remover espaços das pontas dos textos antes de salvar os dados.
* Decidir se o campo `custo` deve aparecer nas respostas da API.
* Substituir o H2 por PostgreSQL.
* Criar a configuração com Docker.

## Projeto em construção

**Cadastro de Produtos API**

```

Esse formato já fica com cara de **README de projeto no GitHub**, sem ficar excessivamente acadêmico.
```
