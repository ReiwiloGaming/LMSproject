package com.oliwierkmiecik.learning_management_system.course.quiz.question;

import com.oliwierkmiecik.learning_management_system.course.quiz.Quiz;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.QuizQuestionAnswer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String questionText;

    @OneToMany(mappedBy = "quizQuestion", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<QuizQuestionAnswer> answers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    private Integer correctAnswer;

    public QuizQuestion() {
    }

    public QuizQuestion(Integer id, String questionText, Quiz quiz, Integer correctAnswer) {
        this.id = id;
        this.questionText = questionText;
        this.quiz = quiz;
        this.correctAnswer = correctAnswer;
    }

    public void addAnswer(QuizQuestionAnswer answer) {
        answers.add(answer);
        answer.setQuizQuestion(this);
    }

    public void removeAnswer(QuizQuestionAnswer answer) {
        answers.remove(answer);
        answer.setQuizQuestion(null);
    }
}
