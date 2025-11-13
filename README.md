# 🚀 GS_FutureOfWork

## 👥 Integrantes
- **RM 557538 – David Cordeiro**
- **RM 555619 – Tiago Morais**
- **RM 557065 – Vinicius Augusto**

## 📖 Descrição do Projeto

O GS API - Sistema de Trilhas e Usuários é uma aplicação backend desenvolvida em Spring Boot com banco de dados OracleSQL, que tem como objetivo gerenciar usuários, trilhas de aprendizado e matrículas.
A API foi projetada para integrar-se a uma interface frontend e fornecer uma base sólida para um sistema educacional ou plataforma corporativa de capacitação.

O sistema implementa autenticação de usuários, controle de níveis de acesso (usuário comum e administrador) e operações completas de CRUD para as entidades principais.
Administradores têm permissão para criar, atualizar e deletar trilhas, enquanto usuários comuns podem listar, buscar e se matricular nas trilhas disponíveis.

A API segue os princípios RESTful e retorna todas as respostas em formato JSON, com tratamento de erros padronizado e mensagens claras para o cliente.

## ✨ Funcionalidades

*   ✅ Cadastro, atualização e remoção de usuários
*   ✅ Autenticação com JWT (JSON Web Token)
*   ✅ Criação e gerenciamento de trilhas e usuários
*   ✅ Consulta detalhada por ID ou email
*   ✅ Log e tratamento de exceções padronizado

## ⚙️ Pré-requisitos

Antes de começar, verifique se você possui instalado:

*   ☕ Java 17+
*   🧰 Maven 3.9+
*   🌐 Postman / Insomnia (para testar a API)

## 🧩 Instalação

### 🔹 1. Clonar o repositório

```bash
git clone https://github.com/davidcordeiro15/API_BioMeasure.git
```

## 📘 Documentação dos Endpoints — `/api/usuarios`

A API de usuários permite cadastrar, autenticar, atualizar e deletar usuários no sistema. Todos os endpoints trabalham com JSON e seguem o padrão REST.

### 🔹 1. Cadastro de Usuário

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/usuarios/cadastrar` | Cria um novo usuário no banco de dados e retorna um token JWT automaticamente após o cadastro. |

**JSON de Requisição**

```json
{
  "nome": "João Silva",
  "email": "joao@teste.com",
  "senha": "123456",
  "estaTrabalhando": 1,
  "eadmin": 0
}
```

**Campos da Requisição**

| Campo | Tipo | Obrigatório | Descrição |
| :--- | :--- | :--- | :--- |
| `nome` | `String` | ✅ | Nome completo do usuário |
| `email` | `String` | ✅ | Email único do usuário |
| `senha` | `String` | ✅ | Senha do usuário |
| `estaTrabalhando` | `Integer (0/1)` | ✅ | Indica se o usuário está empregado |
| `eadmin` | `Integer (0/1)` | ✅ | Define se o usuário é administrador |

**Resposta (200 - Sucesso)**

```json
{
  "mensagem": "Usuário cadastrado com sucesso.",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI..."
}
```

**Resposta (400 - Erro)**

```json
{
  "erro": "Email já cadastrado."
}
```

**Como usar com `curl`**

```bash
curl -X POST http://localhost:8083/api/usuarios/cadastrar \
-H "Content-Type: application/json" \
-d '{
  "nome": "João Silva",
  "email": "joao@teste.com",
  "senha": "123456",
  "estaTrabalhando": 1,
  "eadmin": 0
}'
```

### 🔹 2. Login do Usuário

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/usuarios/login` | Realiza a autenticação de um usuário já existente e retorna um token JWT de acesso. |

**JSON de Requisição**

```json
{
  "email": "joao@teste.com",
  "senha": "123456"
}
```

**Resposta (200 - Sucesso)**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI..."
}
```

**Resposta (401 - Falha de autenticação)**

```json
{
  "erro": "Email ou senha incorretos."
}
```

**Como usar com `curl`**

```bash
curl -X POST http://localhost:8083/api/usuarios/login \
-H "Content-Type: application/json" \
-d '{
  "email": "joao@teste.com",
  "senha": "123456"
}'
```

### 🔹 3. Alterar Dados do Usuário

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `PUT` | `/api/usuarios/alterar` | Atualiza os dados de um usuário com base no email informado. O campo `email` deve ser o mesmo do usuário a ser alterado. |

**JSON de Requisição**

```json
{
  "email": "joao@teste.com",
  "nome": "João da Silva",
  "senha": "novaSenha123",
  "estaTrabalhando": 0,
  "eadmin": 1
}
```

**Campos Atualizáveis**

| Campo | Tipo | Descrição |
| :--- | :--- | :--- |
| `nome` | `String` | Novo nome |
| `senha` | `String` | Nova senha |
| `estaTrabalhando` | `Integer (0/1)` | Atualiza status |
| `eadmin` | `Integer (0/1)` | Define se o usuário é admin |

**Resposta (200 - Sucesso)**

```json
{
  "id": 1,
  "nome": "João da Silva",
  "email": "joao@teste.com",
  "senha": "novaSenha123",
  "estaTrabalhando": 0,
  "eadmin": 1
}
```

**Resposta (400 - Erro)**

```json
{
  "erro": "Usuário não encontrado para o email informado."
}
```

**Como usar com `curl`**

```bash
curl -X PUT http://localhost:8083/api/usuarios/alterar \
-H "Content-Type: application/json" \
-d '{
  "email": "joao@teste.com",
  "nome": "João da Silva",
  "senha": "novaSenha123",
  "estaTrabalhando": 0,
  "eadmin": 1
}'
```

### 🔹 4. Deletar Usuário

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `DELETE` | `/api/usuarios/deletar/{email}` | Exclui um usuário com base no email informado. |

**Parâmetro de Path**

| Parâmetro | Tipo | Descrição |
| :--- | :--- | :--- |
| `email` | `String` | Email do usuário a ser deletado |

**Exemplo de Requisição**

```
DELETE /api/usuarios/deletar/joao@teste.com
```

**Resposta (200 - Sucesso)**

```json
{
  "mensagem": "Usuário deletado com sucesso."
}
```

**Resposta (404 - Não encontrado)**

```json
{
  "erro": "Usuário não encontrado."
}
```

**Como usar com `curl`**

```bash
curl -X DELETE http://localhost:8083/api/usuarios/deletar/joao@teste.com
```

### 🔐 Autenticação (JWT)

Os endpoints `/cadastrar` e `/login` são públicos. Todos os demais devem ser acessados com o token gerado no login.

**Exemplo de Cabeçalho Autenticado**

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI...
```

**Exemplo de requisição autenticada com `curl`**

```bash
curl -X GET http://localhost:8083/api/usuarios \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI..."
```

### 🧾 Resumo dos Endpoints de Usuários

| Método | Endpoint | Descrição | Auth |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/usuarios/cadastrar` | Cria um novo usuário e retorna token | ❌ |
| `POST` | `/api/usuarios/login` | Autentica e retorna token | ❌ |
| `PUT` | `/api/usuarios/alterar` | Atualiza dados do usuário via email | ✅ |
| `DELETE` | `/api/usuarios/deletar/{email}` | Deleta usuário pelo email | ✅ |

---

## 📘 Documentação dos Endpoints — `/api/trilhas`

A API de trilhas gerencia os cursos/trilhas disponíveis no sistema. Ela permite criar, listar, buscar, atualizar, deletar e matricular usuários em trilhas.

Todos os endpoints retornam dados em formato JSON e seguem o padrão RESTful. **A autenticação é feita via email e senha informados no corpo das requisições (sem JWT)**.

### 🔹 1. Cadastrar uma nova trilha

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/trilhas/cadastrar` | Cria uma nova trilha de aprendizado. **Apenas administradores podem cadastrar trilhas.** |

**Corpo da Requisição (JSON)**

```json
{
  "email": "admin@empresa.com",
  "senha": "teste123",
  "trilha": {
    "nome": "Trilha Java Spring Boot",
    "conteudo": "Aprenda desenvolvimento backend com Spring Boot e PostgreSQL.",
    "quantidadeDeMatriculados": 0
  }
}
```

**Campos da Requisição**

| Campo | Tipo | Obrigatório | Descrição |
| :--- | :--- | :--- | :--- |
| `email` | `String` | ✅ | Email do administrador |
| `senha` | `String` | ✅ | Senha do administrador |
| `trilha.nome` | `String` | ✅ | Nome da trilha |
| `trilha.conteudo` | `String` | ✅ | Descrição/conteúdo da trilha |
| `trilha.quantidadeDeMatriculados` | `Integer` | ❌ | Número inicial de matriculados (padrão: 0) |

**Resposta (200 - Sucesso)**

```json
{
  "mensagem": "Trilha cadastrada com sucesso",
  "dados": {
    "id": 1,
    "nome": "Trilha Java Spring Boot",
    "conteudo": "Aprenda desenvolvimento backend com Spring Boot e PostgreSQL.",
    "quantidadeDeMatriculados": 0
  }
}
```

**Resposta (403 - Acesso negado)**

```json
{
  "mensagem": "Apenas administradores podem cadastrar trilhas",
  "dados": null
}
```

### 🔹 2. Listar todas as trilhas

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `GET` | `/api/trilhas/listar` | Retorna todas as trilhas cadastradas no sistema. **Qualquer usuário autenticado pode visualizar.** |

**Exemplo de Resposta (200 - Sucesso)**

```json
{
  "mensagem": "Lista de trilhas obtida com sucesso",
  "dados": [
    {
      "id": 1,
      "nome": "Trilha Java Spring Boot",
      "conteudo": "Aprenda desenvolvimento backend com Spring Boot e PostgreSQL.",
      "quantidadeDeMatriculados": 10
    },
    {
      "id": 2,
      "nome": "Trilha Frontend React",
      "conteudo": "Desenvolvimento de interfaces modernas com React.js.",
      "quantidadeDeMatriculados": 8
    }
  ]
}
```

### 🔹 3. Buscar uma trilha por ID

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/trilhas/buscar/{id}` | Busca uma trilha específica pelo seu ID. **Requer autenticação de qualquer usuário (email e senha no corpo).** |

**Corpo da Requisição (JSON)**

```json
{
  "email": "admin@empresa.com",
  "senha": "teste123"
}
```

**Exemplo de Requisição**

```
POST http://localhost:8083/api/trilhas/buscar/1
```

**Resposta (200 - Sucesso)**

```json
{
  "mensagem": "Trilha encontrada",
  "dados": {
    "id": 1,
    "nome": "Trilha Java Spring Boot",
    "conteudo": "Aprenda desenvolvimento backend com Spring Boot e PostgreSQL.",
    "quantidadeDeMatriculados": 10
  }
}
```

**Resposta (404 - Não encontrada)**

```json
{
  "mensagem": "Trilha não encontrada",
  "dados": null
}
```

### 🔹 4. Atualizar trilha existente

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/trilhas/atualizar/{id}` | Atualiza os dados de uma trilha existente. **Apenas administradores podem alterar trilhas.** |

**Corpo da Requisição (JSON)**

```json
{
  "email": "admin@empresa.com",
  "senha": "teste123",
  "trilha": {
    "nome": "Trilha Java Avançada",
    "conteudo": "Conteúdo atualizado com novos tópicos de Spring Security.",
    "quantidadeDeMatriculados": 15
  }
}
```

**Resposta (200 - Sucesso)**

```json
{
  "mensagem": "Trilha atualizada com sucesso",
  "dados": {
    "id": 1,
    "nome": "Trilha Java Avançada",
    "conteudo": "Conteúdo atualizado com novos tópicos de Spring Security.",
    "quantidadeDeMatriculados": 15
  }
}
```

**Resposta (404 - Não encontrada)**

```json
{
  "mensagem": "Trilha não encontrada",
  "dados": null
}
```

**Resposta (403 - Acesso negado)**

```json
{
  "mensagem": "Apenas administradores podem atualizar trilhas",
  "dados": null
}
```

### 🔹 5. Deletar trilha

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/trilhas/deletar/{id}` | Deleta uma trilha pelo seu ID. **Apenas administradores podem deletar.** |

**Corpo da Requisição (JSON)**

```json
{
  "email": "admin@empresa.com",
  "senha": "teste123"
}
```

**Exemplo de Requisição**

```
POST http://localhost:8083/api/trilhas/deletar/2
```

**Resposta (200 - Sucesso)**

```json
{
  "mensagem": "Trilha deletada com sucesso",
  "dados": null
}
```

**Resposta (404 - Não encontrada)**

```json
{
  "mensagem": "Trilha não encontrada",
  "dados": null
}
```

### 🔹 6. Matricular usuário em uma trilha

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/trilhas/matricular` | Matricula um usuário em uma trilha específica. |

**Parâmetros de Query**

| Parâmetro | Tipo | Obrigatório | Descrição |
| :--- | :--- | :--- | :--- |
| `emailUsuario` | `String` | ✅ | Email do usuário a ser matriculado |
| `idTrilha` | `Integer` | ✅ | ID da trilha desejada |

**Exemplo de Requisição**

```
POST http://localhost:8083/api/trilhas/matricular?emailUsuario=joao@teste.com&idTrilha=1
```

**Resposta (200 - Sucesso)**

```json
{
  "mensagem": "Usuário matriculado com sucesso na trilha Java Spring Boot."
}
```

**Resposta (400 - Erro)**

```json
{
  "erro": "Usuário já matriculado nesta trilha."
}
```

### 🧾 Resumo dos Endpoints de Trilhas

| Método | Endpoint | Descrição | Autenticação | Restrição |
| :--- | :--- | :--- | :--- | :--- |
| `POST` | `/api/trilhas/cadastrar` | Cadastrar nova trilha | ✅ Email/Senha | Somente Admin |
| `GET` | `/api/trilhas/listar` | Listar todas as trilhas | ❌ | Público |
| `POST` | `/api/trilhas/buscar/{id}` | Buscar trilha por ID | ✅ Email/Senha | Usuário autenticado |
| `POST` | `/api/trilhas/atualizar/{id}` | Atualizar trilha existente | ✅ Email/Senha | Somente Admin |
| `POST` | `/api/trilhas/deletar/{id}` | Deletar trilha | ✅ Email/Senha | Somente Admin |
| `POST` | `/api/trilhas/matricular` | Matricular usuário em uma trilha | ✅ Email/Senha | Usuário autenticado |

## 🧱 Estrutura do Projeto

A estrutura do projeto segue o padrão de aplicações Spring Boot, com a organização de pacotes por responsabilidade:

```
src/
├── main/
│   ├── java/com/api/GS/
│   │   ├── controller/      # Controladores REST
│   │   ├── model/           # Entidades (JPA)
│   │   ├── repository/      # Interfaces do Spring Data
│   │   ├── service/         # Lógica de negócio
│   │   └── security/        # JWT, filtros e configs de autenticação
│   └── resources/
│       ├── application.properties # Configurações da aplicação
│       └── templates/             # Templates (se aplicável)
└── test/
    └── java/com/api/GS/         # Testes unitários e de integração
```

## 🧰 Tecnologias Utilizadas

O projeto foi desenvolvido utilizando as seguintes tecnologias e ferramentas:

*   **Java 17**
*   **Spring Boot 3.x**
*   **Spring Data JPA**
*   **Spring Security + JWT** (JSON Web Token)
*   **OracleSQL** (Banco de Dados)
*   **Maven** (Gerenciador de Dependências)
*   **Lombok** (Para reduzir código boilerplate)
*   **Hibernate** (Implementação JPA)

## 🤝 Contribuição

Contribuições são sempre bem-vindas! 🎉 Para contribuir com o projeto, siga os passos abaixo:

1.  Faça um **fork** do projeto.
2.  Crie uma branch para sua feature:
    ```bash
    git checkout -b feature/minha-feature
    ```
3.  Faça o commit das suas alterações:
    ```bash
    git commit -m 'Minha nova feature'
    ```
4.  Envie para o repositório remoto:
    ```bash
    git push origin feature/minha-feature
    ```
5.  Abra um **Pull Request**.

## 🪪 Licença

Distribuído sob a licença **MIT**.

Consulte o arquivo `LICENSE` para mais informações.
