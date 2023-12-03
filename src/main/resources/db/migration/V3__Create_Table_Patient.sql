-- `rest-conexa-challenger`.patient_tb definition

CREATE TABLE IF NOT EXISTS  `patient_tb` (
  `id` binary(16) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `cpf` varchar(14) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(160) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `health_insurance` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_82tvlj9keiob2lwp7aheeyg1` (`cpf`),
  UNIQUE KEY `UK_osuaai4ril6b3ftlyw2vgylo7` (`email`)
) ;
