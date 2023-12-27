# Desafio Técnico backend Conexa

### Subindo a aplicação.

- A aplicação antes de ser levantada será necessario fazer a instalação dos pacotes. use o comando
`mvn clean install` para baixar e instalar os pacotes.
- Agora suba baixe a imagem e suba o container do MySQL para fazer a conexão com o banco de dados, para isso basta navegar
ate a pasta do [docker](docker) e executar o comando `docker-compose up`. Esse comando ira baixar a a imagem do MySQL e 
subir um container.
- Para fazer a conexão com o banco de dados e a aplicação é precisso criar o banco de dados **schedulingappoinment** com
a porta padrão **3306** e com o **username** e **password** como **root**.
- Dentro da aplicação foi usado o Flyway para criar uma migration de alguns pacientes para testes.
- Versão do Java: **17**
- Versão do MySQL: **8.0.35** usando Container Docker

### Endpoint de autorização

Para ter acesso aos enpoints é preciso criar um perfil como doutor, pos é o unico que tem permissão para acessar os endpoints
para  isso basta fazer o seguinte:

Va ate o endpoint **/api/v1/doctors** e crie um usuario dessa maneira

```
{
  "email": "medico@email.com",
  "senha": "umasenhaforte",
  "confirmacaoSenha": "umasenhaforte",
  "especialidade": "CARDIOLOGIST",
  "cpf": "101.202.303-11",
  "dataNascimento": "10/03/1980",
  "telefone": "(21) 3232-6565"
}
```

Dentro da _especialidade_ existe um enumerador na aplicação com todos os tipos de especilidades fornecida no sistema. Apos
isso basta seguir para o endpoint **/api/v1/login** e usar o email e a senha:

```
{
  "email": "medico@email.com",
  "senha": "umasenhaforte",
}
```

Apos isso sera fornecido um token para poder usar acessar os endpoints, sera o Bearer Token.
