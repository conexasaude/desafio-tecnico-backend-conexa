CREATE TABLE IF NOT EXISTS `user_tb` (
  `id` binary(16) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `account_non_expired` bit(1) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `credentials_non_expired` bit(1) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `confirmed_email` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_itqt87x5xy360a5shmi6hwdob` (`user_name`)
);

