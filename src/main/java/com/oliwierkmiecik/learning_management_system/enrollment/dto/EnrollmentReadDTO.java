package com.oliwierkmiecik.learning_management_system.enrollment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentReadDTO {
    private Integer id;
    private String userName;
    private String courseName;
    private Integer completedLessons;
    private String enrollmentDateAsString;

    public EnrollmentReadDTO() {
    }

    public EnrollmentReadDTO(Integer id, String userName, String courseName, Integer completedLessons, String enrollmentDateAsString) {
        this.id = id;
        this.userName = userName;
        this.courseName = courseName;
        this.completedLessons = completedLessons;
        this.enrollmentDateAsString = enrollmentDateAsString;
    }
}