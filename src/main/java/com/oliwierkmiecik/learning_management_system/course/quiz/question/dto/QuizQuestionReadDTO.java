package com.oliwierkmiecik.learning_management_system.course.quiz.question.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizQuestionReadDTO {
    private Integer id;
    private String questionText;
    private List<String> answerTexts;

    public QuizQuestionReadDTO() {
    }

    public QuizQuestionReadDTO(Integer id, String questionText, List<String> answerTexts) {
        this.id = id;
        this.questionText = questionText;
        this.answerTexts = answerTexts;
    }
}
