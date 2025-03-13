package com.oliwierkmiecik.learning_management_system.course.quiz.question.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizQuestionCreateDTO {
    private String questionText;
    private Integer quizId;
    private Integer correctAnswer;

    public QuizQuestionCreateDTO() {
    }

    public QuizQuestionCreateDTO(String questionText, Integer quizId, Integer correctAnswer) {
        this.questionText = questionText;
        this.quizId = quizId;
        this.correctAnswer = correctAnswer;
    }
}
