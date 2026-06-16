# Backend

API REST criada com Spring Boot para gerenciar usuarios, perfis e o vinculo entre eles. O projeto usa Spring Web MVC, Spring Data JPA, validacao Jakarta, MySQL, Lombok e Actuator.

## Configuracao Inicial

Crie o banco de dados:

```sql
CREATE DATABASE financeiro CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Crie o arquivo local de segredos a partir do exemplo:

```bash
cp src/main/resources/application-secrets-example.properties src/main/resources/application-secrets.properties
```

Preencha `src/main/resources/application-secrets.properties` com os valores locais:

```properties
spring.datasource.password=sua_senha_do_mysql
spring.mail.username=seu_email@gmail.com
spring.mail.password=sua_senha_de_app
jwt.secret=uma_string_com_pelo_menos_32_caracteres
jwt.expiration=1000000
```

Esse arquivo esta no `.gitignore` e nao deve ser enviado ao GitHub.

Se o usuario ou a URL do banco forem diferentes, ajuste em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/financeiro
spring.datasource.username=root
```

## Como Executar

Com Maven instalado:

```bash
mvn spring-boot:run
```

Ou usando o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

A API ficara disponivel em:

```text
http://localhost:8080
```

Health check:

```text
GET http://localhost:8080/actuator/health
```

## Endpoints

### Usuarios

| Metodo | Rota | Descricao |
| --- | --- | --- |
| `POST` | `/user` | Cria um usuario |
| `GET` | `/user` | Lista todos os usuarios |
| `GET` | `/user/{id}` | Busca um usuario pelo ID |
| `PATCH` | `/user` | Atualiza nome e e-mail de um usuario |
| `DELETE` | `/user/{id}` | Remove um usuario |
| `PATCH` | `/user/{userId}/profiles/{profileId}` | Vincula um perfil ao usuario |
| `DELETE` | `/user/{userId}/profiles/{profileId}` | Remove o vinculo entre perfil e usuario |
| `GET` | `/user/{userId}/profiles` | Lista os perfis de um usuario |

Exemplo de criacao de usuario:

```json
{
  "name": "Joao Silva",
  "email": "joao@email.com",
  "password": "123456",
  "userProfile": [
    {
      "profile": {
        "id": "UUID_DO_PROFILE"
      }
    }
  ]
}
```

Exemplo de atualizacao de usuario:

```json
{
  "id": 1,
  "name": "Joao Silva Atualizado",
  "email": "joao.atualizado@email.com"
}
```

### Perfis

| Metodo | Rota | Descricao |
| --- | --- | --- |
| `POST` | `/profile` | Cria um perfil |
| `GET` | `/profile` | Lista todos os perfis |
| `GET` | `/profile/{id}` | Busca um perfil pelo UUID |
| `PATCH` | `/profile` | Atualiza a descricao de um perfil |
| `DELETE` | `/profile/{id}` | Remove um perfil |

Exemplo de criacao de perfil:

```json
{
  "description": "ADMIN"
}
```

Exemplo de atualizacao de perfil:

```json
{
  "id": "UUID_DO_PROFILE",
  "description": "USER"
}
```

## Respostas de Erro

Erros tratados retornam um objeto no formato:

```json
{
  "status": 404,
  "messages": "Usuario nao encontrado",
  "datetime": "2026-06-15T22:00:00"
}
```