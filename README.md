# Desafio Técnico backend Conexa - FELIPE

Construir uma API REST para que nossos médicos de plantão consigam se logar na plataforma e agendar atendimentos para pacientes.

Para mais informações e dúvidas do contruido acesse => https://github.com/Felipe-builder/desafio-tecnico-backend-conexa/wiki

## Documentação
Inicie o projeto e acesse o url http://localhost:8080/swagger-ui/index.html para mais detalhes da documentação

## Autenticação

### Signup
Precisamos de uma rota para que os médicos consigam realizar cadastro na plataforma:
```
endpoint:  /api/v1/signup
{
  "full_name": "Mauro Rodrigues",
  "email": "mauro.rodrigues@gmail.com",
  "cpf": "791.347.480-75",
  "password": "senhaNova",
  "confirm_password": "senhaNova",
  "birth_date": "10/03/1980",
  "specialty": "Cardiologista",
  "phone": "(53)8079-4439"
}
```

### Login
Precisamos de uma rota para que médicos da clínica Conexa Saúde consigam realizar login na aplicação:
```
Request:
  POST /api/v1/login
  {
    "email": "medico@email.com",
    "senha": "senhamedico"
  }

Response:
  Status-Code: 200
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
  }
```

### Logoff
Também precisamos de uma rota para o médico conseguir realizar logoff:
```
endpoint: /api/v1/logoff
Authorization: token_jwt
```

### Requisitos para autenticação:
- Utilização de JWT;
- Após logoff, o token precisa ser invalidado, não podendo mais ser permitida a sua utilização;
- Validações de tipos de campo como E-mail, CPF válido, idade apenas números, telefone válido, etc;
- As senhas precisam ser armazenadas de forma criptografada;

## Agendamento

### Criação de atendimento
Precisamos de uma rota onde o médico logado realiza agendamento de consulta para um paciente:
```
endpoint: /api/v1/attendance
Authorization: token_jwt
{
  "date_time": "2023-12-18 23:21:00",
  "patient": {
    "full_name": "Luana Ferreira",
    "cpf": "467.141.940-80"
  }
}
```

### Requisitos para atendimentos:
- Todas as rotas de atendimento precisam passar pela autenticação;
- Só pode ser possível criar agendamentos no futuro;


# Requisitos Obrigatórios:
- Utilização de Java com Spring;
- Banco de dados MySQL;
- Realizar fork deste repositório, e após o desenvolvimento, abrir um merge request apontando para repositório original;


# Serão avaliados os seguintes itens:
* Se todos os requisitos descritos acima foram atendidos;
* Clareza do código;
* Estrutura de código organizada;
* Se utilizou corretamente os conceitos do REST;
* Se utilizou o Spring de forma correta;
* Se possui tratamento de erros;
* Se os testes rodam;

**Obs:**
Necessário descrever, ou prover, todas as dependências e ações necessárias (banco, tabelas, versões, etc) para que o avaliador consiga rodar a aplicação na sua máquina local;

**Lembre-se que uma aplicação boa é uma aplicação bem testada;**

Em caso de dúvidas sobre o desafio, entre em contato.
