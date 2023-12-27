DELIMITER //

CREATE PROCEDURE insertPatient(
    IN p_name VARCHAR(255),
    IN p_cpf VARCHAR(255)
)
BEGIN
    INSERT INTO patient (name, cpf)
    VALUES (p_name, p_cpf);
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE insertattendance(
    IN p_dateHour TIMESTAMP,
    IN p_patient_id BIGINT
)
BEGIN
    INSERT INTO attendance (dateHour, patient_id)
    VALUES (p_dateHour, p_patient_id);
END //

DELIMITER ;

