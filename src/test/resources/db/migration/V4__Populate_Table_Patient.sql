INSERT INTO `patient_tb`
(id, cpf, full_name, health_insurance, created_at, updated_at)
VALUES(UNHEX(REPLACE(UUID(), '-', '')), '123.556.789-01', 'João Silva', 'Bradesco', NOW(), NOW()),
    (UNHEX(REPLACE(UUID(), '-', '')), '987.654.321-02', 'Maria Santos', 'SulAmérica', NOW(), NOW()),
    (UNHEX(REPLACE(UUID(), '-', '')), '111.157.333-03', 'Carlos Rodrigues', 'Unimed', NOW(), NOW()),
    (UNHEX(REPLACE(UUID(), '-', '')), '444.835.666-04', 'Ana Oliveira', 'Amil', NOW(), NOW()),
    (UNHEX(REPLACE(UUID(), '-', '')), '777.888.532-05', 'Lúcia Souza', 'SulAmérica', NOW(), NOW());
