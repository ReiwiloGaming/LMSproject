CREATE TABLE `learning_management_system`.`course` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `description` TEXT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `learning_management_system`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `role` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`));
