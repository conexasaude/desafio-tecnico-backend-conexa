CREATE TABLE IF NOT EXISTS `doctor_tb` (
  `id` binary(16) NOT NULL,
  `full_name` varchar(160) NOT NULL,
  `email` varchar(255) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `specialty` varchar(255) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nuljcnk9e5r5si21i6s03e3c5` (`cpf`),
  UNIQUE KEY `UK_mp715hgutgjlm5g7154o7tr85` (`email`),
  UNIQUE KEY `UK_9bsk3eyburq0mjk8noghn44y8` (`user_id`),
  CONSTRAINT `FKftvqr81o41de0ft9crd5l6tt7` FOREIGN KEY (`user_id`) REFERENCES `user_tb` (`id`)
);

