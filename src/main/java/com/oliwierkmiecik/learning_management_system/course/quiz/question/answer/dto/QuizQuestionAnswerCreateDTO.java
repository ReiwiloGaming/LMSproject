package com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizQuestionAnswerCreateDTO {
    private String answerText;
    private Integer quizQuestionId;

    public QuizQuestionAnswerCreateDTO(String answerText, Integer quizQuestionId) {
        this.answerText = answerText;
        this.quizQuestionId = quizQuestionId;
    }
}
