CREATE TABLE IF NOT EXISTS `user_tb` (
  `id` binary(16) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(160) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `specialty` varchar(255) DEFAULT NULL,
  `cpf` varchar(14) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ng7n54fqsa6bh7b8eia5r4ker` (`cpf`),
  UNIQUE KEY `UK_2dlfg6wvnxboknkp9d1h75icb` (`email`)
);
