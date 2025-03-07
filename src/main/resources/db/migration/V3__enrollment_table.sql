CREATE TABLE `learning_management_system`.`enrollment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  `completed_lessons` INT NOT NULL,
  `enrollment_date` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `enrollment_user_idx` (`user_id` ASC) VISIBLE,
  INDEX `enrollment_course_idx` (`course_id` ASC) VISIBLE,
  CONSTRAINT `enrollment_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `learning_management_system`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `enrollment_course`
    FOREIGN KEY (`course_id`)
    REFERENCES `learning_management_system`.`course` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
