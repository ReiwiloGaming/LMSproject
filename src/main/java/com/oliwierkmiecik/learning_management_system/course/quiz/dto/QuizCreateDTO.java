package com.oliwierkmiecik.learning_management_system.course.quiz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizCreateDTO {
    private String name;
    private Integer courseId;
    private Integer inCoursePosition;

    public QuizCreateDTO() {
    }

    public QuizCreateDTO(String name, Integer courseId, Integer inCoursePosition) {
        this.name = name;
        this.courseId = courseId;
        this.inCoursePosition = inCoursePosition;
    }
}
