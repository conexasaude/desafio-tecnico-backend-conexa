

INSERT INTO `patient_tb`
(id, birth_date, cpf, email, full_name, password, phone, health_insurance)
VALUES(UNHEX(REPLACE(UUID(), '-', '')), '1990-05-15', '123.556.789-01', 'joao.silva2@gmail.com', 'João Silva', 'senha123', '(21) 9876-5432', 'Bradesco'),
    (UNHEX(REPLACE(UUID(), '-', '')), '1985-08-20', '987.654.321-02', 'maria.santos@gmail.com', 'Maria Santos', 'senha456', '(21) 8765-4321', 'SulAmérica'),
    (UNHEX(REPLACE(UUID(), '-', '')), '1982-12-10', '111.157.333-03', 'carlos.rodrigues@gmail.com', 'Carlos Rodrigues', 'senha789', '(21) 7654-3210', 'Unimed'),
    (UNHEX(REPLACE(UUID(), '-', '')), '1998-03-25', '444.835.666-04', 'ana.oliveira@gmail.com', 'Ana Oliveira', 'senhaABC', '(21) 6543-2109', 'Amil'),
    (UNHEX(REPLACE(UUID(), '-', '')), '1977-06-05', '777.888.532-05', 'lucia.souza@gmail.com', 'Lúcia Souza', 'senhaXYZ', '(21) 5432-1098', 'SulAmérica');
