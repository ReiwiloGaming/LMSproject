package com.oliwierkmiecik.learning_management_system.course.quiz.question.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionAnswerRepository extends JpaRepository<QuizQuestionAnswer, Integer> {
    List<QuizQuestionAnswer> findByQuizQuestionId(Integer quizQuestionId);
}
