package com.oliwierkmiecik.learning_management_system.course.quiz;

import com.oliwierkmiecik.learning_management_system.course.Course;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.QuizQuestion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<QuizQuestion> questions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private Integer inCoursePosition;

    public Quiz() {
    }

    public Quiz(Integer id, String name, Course course, Integer inCoursePosition) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.inCoursePosition = inCoursePosition;
    }

    public void addQuestion(QuizQuestion question) {
        questions.add(question);
        question.setQuiz(this);
    }

    public void removeQuestion(QuizQuestion question) {
        questions.remove(question);
        question.setQuiz(null);
    }
}
