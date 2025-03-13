package com.oliwierkmiecik.learning_management_system.course.lesson.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LessonCreateDTO {
    private String name;
    private String content;
    private Integer courseId;
    private Integer inCoursePosition;

    public LessonCreateDTO() {
    }

    public LessonCreateDTO(String name, String content, Integer courseId, Integer inCoursePosition) {
        this.name = name;
        this.content = content;
        this.courseId = courseId;
        this.inCoursePosition = inCoursePosition;
    }
}
