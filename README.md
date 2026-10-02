# ecoEduca — Backend

API REST do **ecoEduca**, um aplicativo educacional de conscientização ambiental com foco no descarte correto de resíduos e em práticas de reciclagem. Os alunos se cadastram, cumprem desafios ambientais publicados no feed, acumulam pontos e disputam um ranking.

Este repositório contém só o backend. O frontend (Angular, publicado na Vercel em `https://ecoeduca-ten.vercel.app`) fica em um repositório separado.

## Tecnologias

| Camada | Tecnologia |
| --- | --- |
| Linguagem | Java 17 |
| Framework | Spring Boot 3.2 (Web, Data JPA, DevTools) |
| Banco de dados | MySQL 8 (usado no Amazon RDS) |
| ORM | Hibernate, com `ddl-auto=update` (as tabelas são criadas e atualizadas sozinhas) |
| Build | Maven (com wrapper `mvnw`) |
| Outros | Jackson, AWS SDK S3 (declarado no `pom.xml`, ainda sem uso no código) |

## Funcionalidades

- **Alunos**: cadastro, consulta, exclusão, troca de nome e de senha.
- **Responsáveis**: CRUD completo dos responsáveis pelos alunos (nome, e-mail, telefone, idade).
- **Login**: autenticação por e-mail e senha, devolvendo o `id` do aluno para o frontend.
- **Ranking**: lista de alunos ordenada pela pontuação.
- **Desafios (postagens)**: feed de desafios com nome, descrição, imagem, pontos e um checklist de tarefas; criação com upload de imagem e exclusão.
- **Imagens**: servidas em `/imagens/**` a partir de `src/main/resources/static/imagens`.

## Estrutura

```
src/main/java/com/ecoeduca/ecoeduca/
├── EcoeducaApplication.java     # ponto de entrada
├── configs/                     # CORS e mapeamento de /imagens/**
├── Controlador/                 # controllers REST
├── Services/                    # regras de negócio
├── JPArepository/               # repositórios Spring Data JPA
└── model/                       # entidades e DTOs
src/main/resources/
├── application.properties       # banco, upload e arquivos estáticos
└── static/imagens/              # imagens dos desafios
```

### Modelo de dados

| Tabela | Entidade | Campos principais |
| --- | --- | --- |
| `alunos` | `Usuario` | `id`, `nome`, `email` (único), `idade`, `senha`, `pontuacao`, `nomeDoResponsavel`, `nomeDoResponsavel2` |
| `responsaveis` | `Responsaveis` | `id`, `nome`, `email` (único), `telefone`, `idade` |
| `postagens` | `Postagens` | `id`, `nome`, `descricao`, `imagemUrl`, `pontos`, `dataCriacao` |
| `checklist` | `CheckListItem` (embutido) | `postagem_id`, `item`, `feito` |

## Como rodar

### Pré-requisitos

- JDK 17 ou mais recente
- Um MySQL 8 acessível (local ou remoto)

### Configuração

As configurações ficam em `src/main/resources/application.properties`. Para não depender dos valores do arquivo, o Spring Boot aceita variáveis de ambiente com o mesmo nome em maiúsculas:

```bash
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/ecoeduca"
export SPRING_DATASOURCE_USERNAME="seu_usuario"
export SPRING_DATASOURCE_PASSWORD="sua_senha"
```

No Windows (PowerShell), use `$env:SPRING_DATASOURCE_URL = "..."`.

O banco `ecoeduca` precisa existir; as tabelas o Hibernate cria na primeira execução.

### Execução

```bash
./mvnw spring-boot:run
```

No Windows, use `mvnw.cmd spring-boot:run`. A API sobe em `http://localhost:8080`.

Para gerar o `.jar`:

```bash
./mvnw clean package
java -jar target/ecoeduca-0.0.1-SNAPSHOT.jar
```

## Endpoints

### Alunos — `/alunos`

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/alunos` | Lista todos os alunos |
| `GET` | `/alunos/{id}` | Busca um aluno |
| `POST` | `/alunos` | Cadastra um aluno (JSON com `nome`, `email`, `idade`, `senha`, `nomeDoResponsavel`...) |
| `DELETE` | `/alunos/{id}` | Remove um aluno |
| `GET` | `/alunos/ranking` | Alunos ordenados pela pontuação, do maior para o menor |
| `POST` | `/alunos/login` | Login que devolve uma mensagem de texto |
| `PUT` | `/alunos/{id}/nome` | Troca o nome (corpo: texto puro) |
| `PUT` | `/alunos/{id}/senha` | Troca a senha (corpo: texto puro) |

### Login — `/login`

| Método | Rota | Descrição |
| --- | --- | --- |
| `POST` | `/login` | Recebe `{ "email", "senha" }` e devolve `{ "idAluno" }`, ou `401` |

### Responsáveis — `/responsaveis`

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/responsaveis` | Lista os responsáveis |
| `GET` | `/responsaveis/{id}` | Busca um responsável |
| `POST` | `/responsaveis` | Cadastra um responsável |
| `PUT` | `/responsaveis/{id}` | Atualiza nome, e-mail e telefone |
| `DELETE` | `/responsaveis/{id}` | Remove um responsável |

### Desafios — `/postagens`

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/postagens` | Feed de desafios, do mais recente para o mais antigo |
| `POST` | `/postagens` | Cria um desafio a partir de JSON (sem upload) |
| `POST` | `/postagens/upload` | Cria um desafio com imagem (`multipart/form-data`: `imagem`, `nome`, `descricao`, `pontos`, `checkList`) |
| `DELETE` | `/postagens/{id}` | Remove o desafio e a imagem dele |
| `GET` | `/postagens/imagens/{fileName}` | Devolve a imagem de um desafio |

O campo `checkList` do upload é um JSON em texto, por exemplo:

```json
[{ "item": "Separar o plástico", "feito": false }, { "item": "Levar ao ponto de coleta", "feito": false }]
```

## CORS

A API só aceita requisições do frontend publicado (`https://ecoeduca-ten.vercel.app`), configurado em `configs/webconfig.java` e nas anotações `@CrossOrigin` dos controllers. Para testar com o frontend rodando localmente, inclua a origem local (por exemplo `http://localhost:4200`) nesses pontos.

## Limitações conhecidas

O projeto foi feito como trabalho acadêmico em 2024 e tem pontos a resolver antes de um uso real:

- **Senhas em texto puro**: são gravadas e comparadas sem hash. O ideal é usar BCrypt (Spring Security).
- **Sem autenticação nas rotas**: o login devolve só o `id` do aluno; não há token, e qualquer cliente pode chamar qualquer endpoint.
- **Caminho de upload fixo**: o upload e a exclusão de imagens usam um caminho absoluto de uma máquina Windows específica, então só funcionam nela. O caminho deveria vir da configuração (ou as imagens irem para o S3, que já está no `pom.xml`).
- **Credenciais no `application.properties`**: o ideal é deixar o arquivo sem senha e usar só variáveis de ambiente.
- **Dois logins**: `/login` e `/alunos/login` fazem quase a mesma coisa; o frontend deve usar `/login`.
- A rota `/postagens/postagens/upload` duplica o upload e pode ser removida.

## Licença

Projeto open source de cunho educacional. Ainda não há um arquivo de licença definido.
