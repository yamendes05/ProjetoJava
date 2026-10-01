# Cadastro de Produtos API

API REST para cadastro de produtos, evolução de um script de automação por cliques de tela. Projeto em construção, começando por um CRUD básico.

## Stack
Java, Spring Boot, Spring Data JPA, H2 (provisório).

## Como rodar
```bash
.\mvnw spring-boot:run
```
O console do banco fica em `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:produtosdb`, usuário `sa`, senha vazia).

## Decisões técnicas

**`BigDecimal` em vez de `double` para dinheiro.** O `double` guarda o número em binário e erra em decimais (`0.1 + 0.2` dá `0.30000000000000004`). O `BigDecimal` guarda o valor exato, com 2 casas no banco (`precision = 12, scale = 2`).

**`codigo` e `tipo` como `String`.** O código pode ter zero à esquerda (`001`) ou letras, e um `int` perderia essa informação. Texto aceita qualquer formato que venha do CSV.

**Construtor sem argumentos na entidade.** Quando o Hibernate lê uma linha do banco, ele cria o objeto vazio e preenche os campos depois. Sem esse construtor, a leitura falha. Ele é `protected` para o resto do código não criar um produto vazio.

## Anotações da entidade
- `@Entity`: marca a classe como mapeada para uma tabela.
- `@Id` + `@GeneratedValue`: define a chave primária, com o valor gerado pelo banco.
- `unique = true`: o banco recusa um segundo produto com o mesmo `codigo` (erro 23505, que o Spring converte em `DataIntegrityViolationException`).