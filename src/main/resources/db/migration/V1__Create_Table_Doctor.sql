CREATE TABLE IF NOT EXISTS `doctor_tb` (
  `id` binary(16) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `cpf` varchar(14) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(160) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `specialty` varchar(255) DEFAULT NULL,
  `medical_license` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nuljcnk9e5r5si21i6s03e3c5` (`cpf`),
  UNIQUE KEY `UK_mp715hgutgjlm5g7154o7tr85` (`email`)
)
