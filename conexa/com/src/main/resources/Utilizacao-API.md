## Para facilitar a usabilidade da API vou deixar os cUrls abaixo: 

## /signup


curl --location 'http://localhost:8080/api/v1/signup' \
--header 'Content-Type: application/json' \
--data-raw '{   
    "nome": "tulio teste",
    "email": "testetuulio@email.com",
    "senha": "111222333",
    "confirmacaoSenha": "111222333",
    "especialidade": "Cardiologista",
    "cpf": "946.309.570-57",
    "dataNascimento": "1995-06-01",
    "telefone": "31991650120"
}'

## /login 

curl --location 'http://localhost:8080/api/v1/login' \
--header 'Content-Type: application/json' \
--data-raw '{       
    "email": "testetuulio@email.com",
    "senha": "111222333"
}'

## /attendance 


## Para criação das tabelas, tem o arquivo create_tables.sql

## Happy fun <3
## Autor @tulio.sathler