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
    "email": "tuluio@email.com",
    "senha": "111222333"
} '

## /attendance 

curl --location 'http://localhost:8080/api/v1/attendance' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0dWx1aW9AZW1haWwuY29tIiwiaWF0IjoxNjc5ODY4MDg4LCJleHAiOjE2Nzk4NzE2ODh9.gvez2Cd9Uj0xmMDc65onylD24auDj_eY0VMrfZ4MP_gjlEmCosyUeZtNChU9Wuah_pvwDxQUdFDiaem4U_1zfQ' \
--header 'Content-Type: application/json' \
--data '{

  "dataHora": "2023-08-02 09:00:00",
  "paciente": {
    "nome": "João Castro",
    "cpf": "239.127.120-42"
  }
}'


## /logoff

curl --location --request POST 'http://localhost:8080/api/v1/logoff' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0dWx1aW9AZW1haWwuY29tIiwiaWF0IjoxNjc5ODY4MDg4LCJleHAiOjE2Nzk4NzE2ODh9.gvez2Cd9Uj0xmMDc65onylD24auDj_eY0VMrfZ4MP_gjlEmCosyUeZtNChU9Wuah_pvwDxQUdFDiaem4U_1zfQ' \
--data ''

## Para criação das tabelas, tem o arquivo create_tables.sql

## Happy fun <3
## Autor @tulio.sathler