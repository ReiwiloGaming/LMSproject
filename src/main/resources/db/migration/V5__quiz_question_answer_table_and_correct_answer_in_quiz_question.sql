ALTER TABLE `learning_management_system`.`quiz_question`
ADD COLUMN `correct_answer` INT NOT NULL AFTER `quiz_id`;

CREATE TABLE `learning_management_system`.`quiz_question_answer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `answer_text` VARCHAR(100) NOT NULL,
  `quiz_question_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `quiz_question_answer_quiz_question_idx` (`quiz_question_id` ASC) VISIBLE,
  CONSTRAINT `quiz_question_answer_quiz_question`
    FOREIGN KEY (`quiz_question_id`)
    REFERENCES `learning_management_system`.`quiz_question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);