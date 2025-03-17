package com.oliwierkmiecik.learning_management_system.course.lesson.dto;

import com.oliwierkmiecik.learning_management_system.exceptions.NullsForbiddenException;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LessonCreateDTO {
    private String name;
    private String content;
    private Integer inCoursePosition;

    public LessonCreateDTO() {
    }

    public LessonCreateDTO(String name, String content, Integer inCoursePosition) {
        this.name = name;
        this.content = content;
        this.inCoursePosition = inCoursePosition;
    }

    public void checkIfAllFieldsPresent() {
        if (this.name == null || this.content == null || this.inCoursePosition == null) {
            throw new NullsForbiddenException("Nulls are forbidden");
        }
    }
}
