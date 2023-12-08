CREATE TABLE IF NOT EXISTS `patient_tb` (
  `id` binary(16) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `full_name` varchar(160) NOT NULL,
  `health_insurance` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_82tvlj9keiob2lwp7aheeyg1` (`cpf`)
);
