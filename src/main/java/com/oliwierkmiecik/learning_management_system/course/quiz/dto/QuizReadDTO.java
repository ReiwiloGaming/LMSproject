package com.oliwierkmiecik.learning_management_system.course.quiz.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizReadDTO {
    private Integer id;
    private String name;
    private List<String> questionTexts;

    public QuizReadDTO() {
    }

    public QuizReadDTO(Integer id, String name, List<String> questionTexts) {
        this.id = id;
        this.name = name;
        this.questionTexts = questionTexts;
    }
}
