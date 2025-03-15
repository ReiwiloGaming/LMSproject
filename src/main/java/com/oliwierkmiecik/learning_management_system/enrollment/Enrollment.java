package com.oliwierkmiecik.learning_management_system.enrollment;

import com.oliwierkmiecik.learning_management_system.course.Course;
import com.oliwierkmiecik.learning_management_system.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private Integer completedLessons;
    private LocalDate enrollmentDate;

    public Enrollment() {
    }

    public Enrollment(User user, Course course, Integer completedLessons, LocalDate enrollmentDate) {
        this.user = user;
        this.course = course;
        this.completedLessons = completedLessons;
        this.enrollmentDate = enrollmentDate;
    }
}