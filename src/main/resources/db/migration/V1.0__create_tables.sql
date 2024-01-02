CREATE TABLE patient
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)        NOT NULL,
    cpf  VARCHAR(11) UNIQUE NOT NULL
);

CREATE TABLE doctor
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    email     VARCHAR(255) UNIQUE NOT NULL,
    password  VARCHAR(255)        NOT NULL,
    specialty VARCHAR(255),
    cpf       VARCHAR(255) UNIQUE NOT NULL,
    birthday  DATE,
    phone     VARCHAR(20)

);

CREATE TABLE attendance
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_hour   TIMESTAMP NOT NULL,
    patient_id BIGINT,
    doctor_id  BIGINT,
    FOREIGN KEY (patient_id) REFERENCES patient (id),
    FOREIGN KEY (doctor_id) REFERENCES doctor (id)
);

CREATE TABLE token_blacklist(
    token VARCHAR(255) PRIMARY KEY NOT NULL
);

