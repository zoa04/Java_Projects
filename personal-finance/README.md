# Personal Finance Control (Java - Spring Boot + JPA)

Projeto exemplo para gerenciar receitas e despesas com persistência via JPA/H2 e uma pequena interface web para visualizar gráficos.

### Funcionalidades
- Registrar transações (receita/despesa) com categoria, valor e data
- CRUD de categorias
- Relatório agregado por categoria (JSON) para alimentar gráficos
- Frontend simples (`src/main/resources/static/index.html`) usando Chart.js
- Banco H2 em modo file (data/finance-db) para persistência local

### Como rodar
1. Java 11+ e Maven instalados.
2. No diretório do projeto: `mvn spring-boot:run`
3. Acesse: `http://localhost:8080/` para a interface simples.
4. H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:file:./data/finance-db`)

### Endpoints REST principais
- `GET /api/categories`
- `POST /api/categories` `{ "name": "Food" }`
- `GET /api/transactions`
- `POST /api/transactions` `{ "type":"EXPENSE","amount":50.0,"date":"2025-10-20","categoryId":1 }`
- `GET /api/reports/spending-by-category`

Arquivos de dados são gravados em `data/`.
