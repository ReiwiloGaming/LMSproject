CREATE TABLE `learning_management_system`.`quiz_question` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `question` VARCHAR(100) NOT NULL,
  `quiz_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `quiz_question_quiz_idx` (`quiz_id` ASC) VISIBLE,
  CONSTRAINT `quiz_question_quiz`
    FOREIGN KEY (`quiz_id`)
    REFERENCES `learning_management_system`.`quiz` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);