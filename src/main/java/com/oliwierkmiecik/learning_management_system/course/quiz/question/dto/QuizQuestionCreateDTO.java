package com.oliwierkmiecik.learning_management_system.course.quiz.question.dto;

import com.oliwierkmiecik.learning_management_system.exceptions.NullsForbiddenException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizQuestionCreateDTO {
    private String questionText;
    private Integer correctAnswer;

    public QuizQuestionCreateDTO() {
    }

    public QuizQuestionCreateDTO(String questionText, Integer correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public void checkIfAllFieldsPresent() {
        if (questionText == null || correctAnswer == null) {
            throw new NullsForbiddenException("Nulls are forbidden");
        }
    }
}
