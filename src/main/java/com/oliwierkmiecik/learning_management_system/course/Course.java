package com.oliwierkmiecik.learning_management_system.course;

import com.oliwierkmiecik.learning_management_system.course.lesson.Lesson;
import com.oliwierkmiecik.learning_management_system.course.quiz.Quiz;
import com.oliwierkmiecik.learning_management_system.enrollment.Enrollment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Quiz> quizes = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private Set<Enrollment> enrollments = new HashSet<>();

    public Course() {
    }

    public Course(Integer id, String name, String description, List<Lesson> lessons, List<Quiz> quizes, Set<Enrollment> enrollments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lessons = lessons;
        this.quizes = quizes;
        this.enrollments = enrollments;
    }


    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        lesson.setCourse(this);
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
        lesson.setCourse(null);
    }

    public void addQuiz(Quiz quiz) {
        quizes.add(quiz);
        quiz.setCourse(this);
    }

    public void removeQuiz(Quiz quiz) {
        quizes.remove(quiz);
        quiz.setCourse(null);
    }
}
