package com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizQuestionAnswerReadDTO {
    private Integer id;
    private String answerText;

    public QuizQuestionAnswerReadDTO(Integer id, String answerText) {
        this.id = id;
        this.answerText = answerText;
    }
}
