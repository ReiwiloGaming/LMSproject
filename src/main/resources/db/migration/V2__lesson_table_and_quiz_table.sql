CREATE TABLE `learning_management_system`.`lesson` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `content` TEXT NOT NULL,
  `course_id` INT NOT NULL,
  `in_course_position` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `lesson_course_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `lesson_course`
    FOREIGN KEY (`course_id`)
    REFERENCES `learning_management_system`.`course` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `learning_management_system`.`quiz` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `course_id` INT NOT NULL,
  `in_course_position` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `quiz_course_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `quiz_course`
    FOREIGN KEY (`course_id`)
    REFERENCES `learning_management_system`.`course` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
