package com.oliwierkmiecik.learning_management_system.course.lesson.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonReadDTO {
    private Integer id;
    private String name;
    private String content;

    public LessonReadDTO() {
    }

    public LessonReadDTO(Integer id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }
}
