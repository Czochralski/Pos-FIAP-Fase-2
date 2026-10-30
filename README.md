# Restaurantes API

API REST desenvolvida em Java 21 e Spring Boot para gerenciamento de restaurantes, tipos de usuário e itens de cardápio. O projeto faz parte do Tech Challenge da Fase 2 da pós-graduação FIAP e utiliza Clean Architecture para separar regras de negócio, interfaces e detalhes de infraestrutura.

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC e Spring Data JPA / Hibernate
- Spring Security com HTTP Basic
- PostgreSQL 17 e Flyway
- Springdoc OpenAPI / Swagger UI
- Maven Wrapper
- Docker e Docker Compose
- JUnit, Spring MockMvc, H2 e JaCoCo
- Postman para chamadas e validação manual da API

## Pré-requisitos

Para executar a aplicação com Docker Compose, instale:

| Requisito | Descrição |
|---|---|
| Docker Desktop | Necessário para executar os containers da API e do PostgreSQL. |
| Git | Necessário para clonar o repositório. |
| Postman | Recomendado para executar e validar os testes da API. |


Para executar a aplicação diretamente no computador, também é necessário ter Java 21. O projeto inclui Maven Wrapper, portanto não é necessário instalar Maven separadamente.

## Estrutura do projeto

```text
src/
├── main/
│   ├── java/com/czo/restaurantes_api/
│   │   ├── core/
│   │   │   ├── controller/
│   │   │   ├── domain/
│   │   │   ├── dto/
│   │   │   ├── exception/
│   │   │   ├── gateway/
│   │   │   └── usecase/
│   │   ├── infra/
│   │   │   ├── config/
│   │   │   ├── database/jpa/
│   │   │   ├── security/
│   │   │   └── web/
│   │   └── RestaurantesApiApplication.java
│   └── resources/db/migration/
└── test/java/
```

## Arquitetura — Clean Architecture

O projeto segue a **Clean Architecture**: as regras de negócio ficam no núcleo e não dependem de Spring, HTTP, JPA ou PostgreSQL. A regra de dependência aponta para dentro: a infraestrutura implementa os contratos definidos pelo núcleo.

### Regras de negócio e contratos internos

- **`core.domain` — regras de negócio essenciais:** entidades e invariantes de `Restaurante`, `Endereco`, `TipoUsuario` e `ItemCardapio`.
- **`core.usecase` — regras da aplicação:** casos de uso que coordenam operações, aplicam autorização e usam as interfaces de saída.
- **`core.gateway` — portas de saída:** contratos de persistência que o núcleo precisa; não conhecem JPA ou Spring Data.
- **`core.dto` — modelos de dados dos casos de uso:** dados de entrada e saída usados no limite entre a API e os casos de uso.
- **`core.controller` — fachadas de entrada da aplicação:** encaminham as chamadas recebidas para os casos de uso.

### Adaptadores e infraestrutura

- **`infra.web` — adaptador de entrada HTTP:** recebe as requisições REST, valida e converte JSON para os modelos de entrada do núcleo e transforma os resultados em respostas HTTP.
- **`infra.database.jpa` — adaptador de saída para persistência:** implementa as portas `core.gateway` usando repositórios Spring Data JPA. Os mappers convertem entre o domínio e as entidades JPA.
- **`infra.security` — adaptador de autenticação:** integra os usuários existentes com Spring Security e fornece o principal autenticado à API.
- **`infra.config` — composição da aplicação:** configura segurança e cria os componentes, conectando os casos de uso aos adaptadores.
- **Spring MVC, Spring Security, JPA, Flyway e PostgreSQL** são detalhes externos de entrega e persistência.

## Versionamento e endpoints

A API utiliza o prefixo `/v1`. Os identificadores nas rotas são UUIDs. Todas as rotas abaixo exigem autenticação HTTP Basic, exceto Swagger/OpenAPI.

### Autenticação

| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| GET | `/v1/auth/me` | Qualquer usuário autenticado | Retorna o ID, o login e o tipo do usuário autenticado. |

### Tipos de usuário

| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| POST | `/v1/tipo-usuario` | `DONO` | Cria um tipo personalizado. |
| GET | `/v1/tipo-usuario` | Usuário autenticado | Lista os tipos cadastrados. |
| GET | `/v1/tipo-usuario/{id}` | Usuário autenticado | Busca um tipo pelo UUID. |
| PUT | `/v1/tipo-usuario/{id}` | `DONO` | Atualiza um tipo personalizado. |
| DELETE | `/v1/tipo-usuario/{id}` | `DONO` | Exclui um tipo personalizado. |

Os tipos de sistema `CLIENTE` e `DONO` são obrigatórios e não podem ser alterados ou excluídos. A associação inicial entre usuários e tipos é feita pelas migrations; a API não expõe CRUD de usuários nem endpoint para administrar essa associação.

### Restaurantes

| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| POST | `/v1/restaurantes` | `DONO` | Cadastra um restaurante para o usuário autenticado. |
| GET | `/v1/restaurantes` | Usuário autenticado | Lista os restaurantes. |
| GET | `/v1/restaurantes/{id}` | Usuário autenticado | Busca um restaurante pelo UUID. |
| PUT | `/v1/restaurantes/{id}` | Proprietário autenticado | Atualiza um restaurante. |
| DELETE | `/v1/restaurantes/{id}` | Proprietário autenticado | Exclui um restaurante. |

O proprietário é identificado pelas credenciais da requisição. Não envie o ID do usuário no corpo.

Exemplo de corpo para criar ou atualizar:

```json
{
  "nome": "Restaurante Central",
  "endereco": {
    "rua": "Rua das Flores",
    "numero": "123",
    "bairro": "Centro",
    "cidade": "Recife",
    "estado": "PE",
    "cep": "50000-000"
  },
  "tipoCozinha": "Brasileira",
  "horarioFuncionamento": "10:00 às 22:00"
}
```

### Itens de cardápio

Os itens são acessados pela rota do restaurante ao qual pertencem.

| Método | Endpoint | Acesso | Descrição |
|---|---|---|---|
| POST | `/v1/restaurantes/{restauranteId}/itens-cardapio` | Proprietário autenticado | Adiciona um item ao cardápio. |
| GET | `/v1/restaurantes/{restauranteId}/itens-cardapio` | Usuário autenticado | Lista os itens do restaurante. |
| GET | `/v1/restaurantes/{restauranteId}/itens-cardapio/{itemCardapioId}` | Usuário autenticado | Busca um item pelo UUID. |
| PUT | `/v1/restaurantes/{restauranteId}/itens-cardapio/{itemCardapioId}` | Proprietário autenticado | Atualiza um item. |
| DELETE | `/v1/restaurantes/{restauranteId}/itens-cardapio/{itemCardapioId}` | Proprietário autenticado | Exclui um item. |

Exemplo de corpo para criar ou atualizar:

```json
{
  "nome": "Baião de dois",
  "descricao": "Prato da casa",
  "preco": 32.50,
  "apenasNoRestaurante": false,
  "foto": "https://exemplo.com/imagens/baiao.jpg"
}
```

## Autenticação e autorização

A API usa HTTP Basic. No Postman, selecione **Authorization → Basic Auth** e informe login e senha. Também é possível usar o botão **Authorize** na interface Swagger.

Usuários de desenvolvimento inseridos pelas migrations:

| Login | Senha | Tipo |
|---|---|---|
| `cliente` | `123456` | `CLIENTE` |
| `dono` | `123456` | `DONO` |
| `dono2` | `123456` | `DONO` |

Essas credenciais são apenas para desenvolvimento e testes locais.

- Todos os endpoints da API exigem autenticação; apenas a documentação Swagger/OpenAPI é pública.
- Somente usuários `DONO` podem cadastrar restaurantes e administrar tipos de usuário.
- Alterações e exclusões de restaurantes e itens de cardápio exigem que o usuário autenticado seja o proprietário do restaurante.
- Os tipos de sistema `CLIENTE` e `DONO` são protegidos contra alteração e exclusão.

## Banco de dados

A aplicação utiliza PostgreSQL 17. O Flyway controla a criação e evolução do schema, enquanto o Hibernate valida a estrutura (`ddl-auto: validate`). As migrations ficam em `src/main/resources/db/migration`:

| Migration | Conteúdo |
|---|---|
| `V1__tabelas.sql` | Cria as tabelas e relacionamentos da aplicação. |
| `V2__inserts.sql` | Insere os tipos de sistema, usuários e endereços de desenvolvimento. |
| `V3__marcar_tipos_usuario_sistema.sql` | Marca `CLIENTE` e `DONO` como tipos protegidos. |

## Variáveis de ambiente

O Docker Compose aceita estas variáveis, usando os valores abaixo quando não forem definidas:

```dotenv
DATASOURCE_DB=restaurantes
DATASOURCE_USERNAME=postgres
DATASOURCE_PASSWORD=postgres
```

Elas podem ser definidas no ambiente ou em um arquivo `.env` na raiz do projeto. O arquivo `.env` não deve ser versionado. A conexão padrão da aplicação executada diretamente no computador é `jdbc:postgresql://localhost:5432/restaurantes`; para alterá-la, defina também `DATASOURCE_URL`.

## Execução com Docker Compose

Com Docker em execução, na raiz do projeto:

```powershell
docker compose up --build -d
```

O Compose constrói a imagem Java, inicia PostgreSQL e API e aguarda o banco ficar saudável antes de iniciar a aplicação. O Dockerfile usa Java 21 e extrai o JAR em layers para que dependências e código da aplicação sejam armazenados separadamente na imagem.

A API fica disponível em `http://localhost:8080`. Para acompanhar os logs:

```powershell
docker compose logs -f restaurantes-api
```

Para parar os containers:

```powershell
docker compose down
```

Os dados do banco ficam no volume `postgres_data`. Para remover o volume e os dados persistidos também:

```powershell
docker compose down -v
```

## Swagger / OpenAPI

- Swagger UI: <http://localhost:8080/swagger-ui.html>
- Especificação OpenAPI em JSON: <http://localhost:8080/v3/api-docs>

Os dois endereços ficam públicos. Para executar endpoints da API pela interface, use **Authorize** e informe as credenciais HTTP Basic.

## Postman

Para testar a API pelo Postman, use a URL base `http://localhost:8080` e configure **Authorization → Basic Auth** com uma das contas de desenvolvimento. Os endpoints e exemplos de corpo estão descritos nas seções acima e também na interface Swagger.

## Testes e cobertura

O projeto contém testes unitários para domínio e casos de uso e testes de integração para endpoints, segurança e persistência. O Maven Wrapper executa a suíte:

```powershell
.\mvnw.cmd test
.\mvnw.cmd verify
```

No Linux ou macOS:

```bash
./mvnw test
./mvnw verify
```

O JaCoCo gera o relatório HTML em `target/site/jacoco/index.html`. A meta mínima de cobertura de linhas configurada no build é 80%.

## Respostas de erro

Os erros são devolvidos no formato `application/problem+json`:

| Status | Significado |
|---|---|
| `400 Bad Request` | Corpo inválido ou campo que não passou na validação. |
| `401 Unauthorized` | Credenciais ausentes ou inválidas. |
| `403 Forbidden` | Usuário autenticado sem permissão para a operação ou que não é proprietário do recurso. |
| `404 Not Found` | Restaurante, tipo ou item não encontrado. |
| `409 Conflict` | Tipo duplicado ou tentativa de alterar/excluir um tipo de sistema. |
