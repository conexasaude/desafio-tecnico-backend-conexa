# API Rest em Java com Spring Boot e MySQL (Dockerizado)

Este projeto é uma API Rest desenvolvida em Java 17 com o Spring Boot 3.2.0, utilizando MySQL 8.0.35 dockerizado.

## Pré-requisitos

Certifique-se de ter o seguinte instalado antes de prosseguir:

- Docker
- Java 17
- IntelliJ IDEA (ou outra IDE de sua preferência)
- Postman (ou qualquer outro cliente HTTP para testar as rotas)

## Passo a passo

1. **Clone o repositório:**
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    cd nome_do_seu_projeto
    ```

2. **Iniciar o banco de dados MySQL com Docker:**
   - 
   - No arquivo `compose.yml` set as environments.
   - Certifique-se de substituir `sua_senha` pela senha desejada e `nome_do_banco` pelo nome do seu banco de dados.

3. **Importe o projeto no IntelliJ IDEA:**
    - 
    - Abra o IntelliJ IDEA e selecione `File > Open`.
    - Navegue até o diretório do seu projeto clonado e selecione a pasta.
    - Aguarde até que o IntelliJ importe o projeto.

4. **Configuração da aplicação:**
    - 
    - No arquivo `application.yml`, defina as configurações do banco de dados para conectar à instância Docker do MySQL:

    ```yaml
    spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco?useSSL=false
    spring.datasource.username=root
    spring.datasource.password=sua_senha
    ```

5. **Execute a aplicação Spring Boot:**
    - 
    - No IntelliJ, localize a classe principal (`@SpringBootApplication`) e clique com o botão direito.
    - Selecione `Run <Aplication>`. Isso iniciará sua aplicação na porta padrão 8080. Certifique-se de que a porta 8080 esteja disponível.

6. **Observações sobre autenticação:**
    - 
    - Para as rotas `/logoff` e `/attendance`, é necessário incluir um token JWT na barra de autorização do Postman.
    - Lembra-se de colocar a palavra Bearer na frente do codigo token.
    ```
    Bearer <codigoToken>
    ```