package com.oliwierkmiecik.learning_management_system.course.lesson;

import com.oliwierkmiecik.learning_management_system.course.Course;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private Integer inCoursePosition;

    public Lesson() {
    }

    public Lesson(Integer id, String name, String content, Course course, Integer inCoursePosition) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.course = course;
        this.inCoursePosition = inCoursePosition;
    }
}
