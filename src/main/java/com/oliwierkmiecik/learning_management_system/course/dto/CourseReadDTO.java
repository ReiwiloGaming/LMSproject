package com.oliwierkmiecik.learning_management_system.course.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseReadDTO {
    private Integer id;
    private String name;
    private String description;

    public CourseReadDTO() {
    }

    public CourseReadDTO(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
