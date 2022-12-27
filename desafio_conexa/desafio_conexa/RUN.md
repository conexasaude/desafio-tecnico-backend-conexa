### PARA RODAR

É necessário criar um banco de dados MySQL chamado 'conexa_saude' e dentro desse banco criar as 3 tabelas utilizadas no
desafio.

	CREATE TABLE `consulta` (
  `cd_consulta` int NOT NULL AUTO_INCREMENT,
  `dt_hora` datetime DEFAULT NULL,
  `cd_paciente` int DEFAULT NULL,
  PRIMARY KEY (`cd_consulta`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `medico` (
  `cd_medico` int NOT NULL AUTO_INCREMENT,
  `tx_email` varchar(60) DEFAULT NULL,
  `tx_senha` varchar(60) DEFAULT NULL,
  `tx_especialidade` varchar(60) DEFAULT NULL,
  `tx_cpf` varchar(14) DEFAULT NULL,
  `dt_nascimento` datetime DEFAULT NULL,
  `tx_telefone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`cd_medico`),
  UNIQUE KEY `tx_email_UNIQUE` (`tx_email`),
  UNIQUE KEY `tx_cpf_UNIQUE` (`tx_cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `paciente` (
  `cd_paciente` int NOT NULL AUTO_INCREMENT,
  `tx_cpf` varchar(14) DEFAULT NULL,
  `tx_nome` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`cd_paciente`),
  UNIQUE KEY `tx_cpf_UNIQUE` (`tx_cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


As configurações de conexão com o banco podem ser alteradas no arquivo 'application.yml' dentro do caminho src/main/resources. A versão do Java utilizada foi o Java 18. Mais informações podem ser encontradas no link da documentação Swagger 'http://localhost:8080/swagger-ui/index.html' após rodar a aplicação.

Para realizar o uso do token de acesso é necessário adicionar um paramêtro no header da requisição chamado Authorization cujo valor é Bearer {JwtToken}.