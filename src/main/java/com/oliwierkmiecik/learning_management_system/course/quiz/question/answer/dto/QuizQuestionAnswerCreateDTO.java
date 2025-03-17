package com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto;

import com.oliwierkmiecik.learning_management_system.exceptions.NullsForbiddenException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizQuestionAnswerCreateDTO {
    private String answerText;

    public QuizQuestionAnswerCreateDTO(String answerText) {
        this.answerText = answerText;
    }

    public void checkIfAllFieldsPresent() {
        if (this.answerText == null) {
            throw new NullsForbiddenException("Nulls are forbidden");
        }
    }
}
