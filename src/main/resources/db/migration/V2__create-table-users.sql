CREATE TABLE users (
    id VARCHAR(255) PRIMARY KEY UNIQUE NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    especialidade VARCHAR(50),
    cpf VARCHAR(14),
    data_nascimento DATETIME,
    telefone VARCHAR(20)
);