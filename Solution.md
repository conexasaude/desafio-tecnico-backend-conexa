### Desafio conexa - solução

Aplicação de agendamento de consultas pelo médico (usuário). Para autenticação e autorização,
utilizei de alguns recursos do Spring Security, realizando o filtro de requisições baseada em tokens.

Arquitetura:

```text
src
├───main
│   ├───java
│   │   └───com
│   │       └───conexa
│   │           └───desafio
│   │               ├───config
│   │               ├───controllers
│   │               ├───models
│   │               ├───payload
│   │               ├───repository
│   │               ├───security
│   │               ├───services
│   │               └───validators
│   └───resources
│       ├───static
│       └───templates
└───test
    ├───java
    │   └───com
    │       └───conexa
    │           └───desafio
    │               ├───controllers
    │               ├───helpers
    │               └───validators
    └───resources
```

* Autenticação e autorização

A validação dos tokens foi realizada InMemory, mas o token anterior foi salvo no banco de dados
para estratégia de logoff. Poderia ter utilizado alguma tecnologia como redis, mas neste caso armazenei
os dados em uma tabela com join para usuário no mysql, apenas para simplificar o uso, sem a necessidade
de adicionar mais dependências ao projeto.

* Entidades representadas

Decidi abstrair as requisições em três entidades principais: Usuario(médicos da plataforma),
Consulta(consultas agendadas), Paciente(pacientes agendados nas consultas). Por último, criei uma entidade
para armazenar tokens do usuário, conforme descrito acima. Token(para logoff)

### **Nota importante**

```text
Todos os endpoints possuem o mesmo padrão de retorno, utilizando a classe BaseResponse. Esta foi uma
decisão tomada para facilitar o entendimento do usuário (dev) a erros e possuir uma padronização para consumo da API.
```

#### Como executar o projeto

Existem dois arquivos DockerFile dentro do projeto, um para ambiente de desenvolvimento e outro que empacota a
aplicação para testes.
No momento não existem grandes mudanças para testar o projeto com estes ambientes diferentes, mas a ideia é ter
a arquitetura pronta para a necessidade.

Ao executar a aplicação, ela será exposta na porta 8080 da sua máquina, e assim poderá realizar os testes.
Comandos para executar:

```shell
docker compose -f docker-compose-dev.yml up
```

ou

```shell
docker compose -f docker-compose.yml up
```

Caso queira realizar consultas ao `mysql`, ele estará disponível na porta `3306` da sua máquina.