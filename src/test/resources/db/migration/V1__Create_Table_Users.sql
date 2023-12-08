-- `rest-conexa-challenger`.user_tb definition

CREATE TABLE IF NOT EXISTS `user_tb` (
  `id` binary(16) NOT NULL,
  `account_non_expired` bit(1) DEFAULT NULL,
  `account_non_locked` bit(1) DEFAULT NULL,
  `credentials_non_expired` bit(1) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_itqt87x5xy360a5shmi6hwdob` (`user_name`)
);