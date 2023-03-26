
CREATE TABLE attendance (
	id_attendance varchar(36) PRIMARY KEY,
    id_paciente varchar(36) not null ,
    data_hora timestamp NOT null,
    FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente)
);

CREATE TABLE paciente (

    id_paciente varchar(36) PRIMARY KEY, 
    nome varchar(50) not null,
    cpf varchar(11) NOT NULL UNIQUE
);

CREATE TABLE doctor (
    id_medico varchar(36) PRIMARY KEY, 
    nome varchar(50) not null,
	email varchar(50) NOT NULL UNIQUE,
    senha varchar(240) NOT NULL,
    especialidade varchar(30) NOT NULL,
    cpf varchar(11) NOT NULL UNIQUE, 
    dt_nascimento Date NOT NULL,
    telefone varchar(11) NOT NULL
);

