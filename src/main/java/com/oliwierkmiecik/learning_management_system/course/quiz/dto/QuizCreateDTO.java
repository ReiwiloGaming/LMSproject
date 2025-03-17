package com.oliwierkmiecik.learning_management_system.course.quiz.dto;

import com.oliwierkmiecik.learning_management_system.exceptions.NullsForbiddenException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizCreateDTO {
    private String name;
    private Integer inCoursePosition;

    public QuizCreateDTO() {
    }

    public QuizCreateDTO(String name, Integer inCoursePosition) {
        this.name = name;
        this.inCoursePosition = inCoursePosition;
    }

    public void checkIfAllFieldsPresent() {
        if (this.name == null || this.inCoursePosition == null) {
            throw new NullsForbiddenException("Nulls are forbidden");
        }
    }
}
