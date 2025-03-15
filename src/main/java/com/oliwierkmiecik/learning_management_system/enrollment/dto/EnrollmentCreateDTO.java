package com.oliwierkmiecik.learning_management_system.enrollment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EnrollmentCreateDTO {
    private Integer userId;
    private Integer courseId;
    private LocalDate enrollmentDate = LocalDate.now();
    private Integer completedLessons = 0;

    public EnrollmentCreateDTO() {
    }

    public EnrollmentCreateDTO(Integer userId, Integer courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }
}