INSERT INTO `doctor_tb` (`id`, `birth_date`, `cpf`, `email`, `full_name`, `password`, `phone`, `specialty`, `created_at`, `updated_at`) VALUES
    (UNHEX(REPLACE(UUID(), '-', '')), '1975-02-11', '123.456.789-09', 'francisco_oliveira@gmail.com', 'Francisco Oliveira', '?medico@Oliv', '(21) 3232-6262', 'Neurologista', NOW(), NOW()),
    (UNHEX(REPLACE(UUID(), '-', '')), '1980-03-10', '987.654.321-00', 'givonaldo_pereira0@gmail.com', 'Givonaldo Pereira 123', NULL, '(21) 3232-6562', 'Cardiologista - SR', NOW(), NOW()),
    (UNHEX(REPLACE(UUID(), '-', '')), '1980-03-10', '111.222.333-44', 'givonaldo_pereira5@gmail.com', 'Givonaldo Pereira 5', '?medico@GivonNEW', '(21) 3232-6562', 'Cardiologista', NOW(), NOW()),
    (UNHEX(REPLACE(UUID(), '-', '')), '1995-02-11', '000.111.222-33', 'marcia_oliveira@gmail.com', 'Marcia Oliveira', '?medico@Marc', '(21) 3232-6261', 'Ginecologista', NOW(), NOW()),
    (UNHEX(REPLACE(UUID(), '-', '')), '1980-03-10', '555.444.333-22', 'givonaldo_pereira2@gmail.com', 'Givonaldo Pereira 2', '?medico@Givon', '(21) 3232-6562', 'Cardiologista', NOW(), NOW());

