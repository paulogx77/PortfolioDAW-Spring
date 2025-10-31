# Projeto de API para Portfólio (DAW - IFPB)

Este projeto consiste na API REST para uma aplicação de portfólio, permitindo o gerenciamento de usuários, perfis, projetos e comentários.

O objetivo principal desta fase do projeto foi refatorar a camada de persistência para **remover as abstrações do Spring Data JPA** e implementar um padrão **Data Access Object (DAO) Puro**, utilizando um template base (`AbstractDAOImpl`) fornecido pelo professor.

## 🚀 Tecnologias Utilizadas

* **Java 21**
* **Docker Compose** para gerenciamento do banco de dados.
* **JPA (Jakarta Persistence)** como especificação de persistência.
* **Hibernate** como provedor da JPA.
* **PostgreSQL** como banco de dados relacional.
* **Spring Boot** (como base do projeto, embora a camada de persistência tenha sido desacoplada).
* **Maven** para gerenciamento de dependências.

## 🎯 Conformidade com as Regras da Atividade

O projeto foi estruturado para seguir rigorosamente o conjunto de regras definido para esta atividade:

1.  **Mapeamento de Entidades (Regra 2):**
    * Todas as quatro entidades principais (`User`, `Project`, `Profile`, `Comment`) foram mapeadas como entidades JPA (`@Entity`).

2.  **Remoção de Relacionamentos (Regra 3 e 4):**
    * Conforme solicitado, **todos os mapeamentos de relacionamento** (`@OneToOne`, `@OneToMany`, etc.) foram removidos das classes de entidade. A persistência nesta fase trata cada entidade de forma isolada.

3.  **Implementação do Padrão DAO (Regras 8 e 9):**
    * Para cada entidade (ex: `User`), foi criada uma interface `UserDAO` que estende a interface base `br.edu.ifpb.es.daw.dao.DAO`.
    * Para cada interface, foi criada uma implementação concreta (ex: `UserDAOImpl`) que estende a classe base `br.edu.ifpb.es.daw.dao.impl.AbstractDAOImpl`.

4.  **Implementação Manual de `equals`, `hashCode` e `toString` (Regras 10, 11, 12):**
    * Como o uso do Lombok era restrito, os métodos `equals()`, `hashCode()` (baseados apenas no `id`) e `toString()` foram implementados manually em todas as quatro entidades.

5.  **Classes de Teste `Main` (Regras 5 e 6):**
    * Para validar a camada de persistência sem depender do Spring, foram criadas 8 classes de execução no pacote `main`:
        * `MainUserSave` / `MainUserDeleteAll`
        * `MainProjectSave` / `MainProjectDeleteAll`
        * `MainProfileSave` / `MainProfileDeleteAll`
        * `MainCommentSave` / `MainCommentDeleteAll`

6.  **Garantia de Re-execução (Regra 13):**
    * As classes `Main*Save` que persistem entidades com restrições de unicidade (como `User.email`) utilizam uma estratégia de geração de valor único (ex: `System.nanoTime()`) para garantir que possam ser executadas múltiplas vezes sem violar as restrições do banco.

## 🐳 Executando o Banco de Dados com Docker Compose

Para facilitar a execução e garantir um ambiente de banco de dados consistente, este projeto inclui um arquivo `docker-compose.yml`.

1.  **Pré-requisitos:** Tenha o [Docker](https://www.docker.com/products/docker-desktop/) instalado e em execução na sua máquina.
2.  **Ajuste as Credenciais:** No arquivo `docker-compose.yml`, defina seu `POSTGRES_USER` e `POSTGRES_PASSWORD` desejados.
3.  **Inicie o Banco:** No terminal, na raiz do projeto (mesmo local do `docker-compose.yml`), execute o comando:
    ```bash
    docker-compose up -d
    ```
4.  O Docker irá baixar a imagem do PostgreSQL e iniciar um contêiner chamado `portfolio-db` em segundo plano, acessível em `localhost:5432`.

## 🛠️ Como Executar e Testar

Este projeto não deve ser executado através da classe `PortfolioApplication` (Spring Boot) para esta atividade. A validação é feita através das classes `Main`.

### 1. Configurar o Banco de Dados (`persistence.xml`)

Toda a configuração de conexão com o banco de dados está centralizada no arquivo:

`src/main/resources/META-INF/persistence.xml`

**IMPORTANTE:** Garanta que as credenciais neste arquivo sejam **idênticas** às que você definiu no `docker-compose.yml`.

```xml
<persistence-unit name="portfolio" ...>

    <property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/portfolio_db"/>
    <property name="jakarta.persistence.jdbc.user" value="paulo"/>
    <property name="jakarta.persistence.jdbc.password" value="tresdois"/>
    
    <property name="hibernate.hbm2ddl.auto" value="create"/>
</persistence-unit>
