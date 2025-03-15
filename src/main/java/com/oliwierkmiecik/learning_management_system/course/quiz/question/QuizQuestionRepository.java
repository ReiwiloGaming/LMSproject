package com.oliwierkmiecik.learning_management_system.course.quiz.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Integer>{
    List<QuizQuestion> findByQuizId(Integer quizId);
}
