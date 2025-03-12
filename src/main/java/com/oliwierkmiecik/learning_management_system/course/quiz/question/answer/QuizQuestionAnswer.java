package com.oliwierkmiecik.learning_management_system.course.quiz.question.answer;

import com.oliwierkmiecik.learning_management_system.course.quiz.question.QuizQuestion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class QuizQuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String answerText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_question_id", nullable = false)
    private QuizQuestion quizQuestion;

    public QuizQuestionAnswer() {
    }

    public QuizQuestionAnswer(Integer id, String answerText, QuizQuestion quizQuestion) {
        this.id = id;
        this.answerText = answerText;
        this.quizQuestion = quizQuestion;
    }
}
