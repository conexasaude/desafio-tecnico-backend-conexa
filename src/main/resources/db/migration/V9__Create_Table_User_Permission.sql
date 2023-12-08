CREATE TABLE IF NOT EXISTS `user_permission_tb` (
  `id_user` binary(16) NOT NULL,
  `id_permission` binary(16) NOT NULL,
  PRIMARY KEY (`id_user`,`id_permission`),
  KEY `fk_user_permission_permission` (`id_permission`),
  CONSTRAINT `fk_user_permission` FOREIGN KEY (`id_user`) REFERENCES `user_tb` (`id`),
  CONSTRAINT `fk_user_permission_permission` FOREIGN KEY (`id_permission`) REFERENCES `permission_tb` (`id`)
) ENGINE=InnoDB;

