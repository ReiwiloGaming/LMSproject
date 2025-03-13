package com.oliwierkmiecik.learning_management_system.course.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseCreateDTO {
    private String name;
    private String description;

    public CourseCreateDTO() {
    }

    public CourseCreateDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
