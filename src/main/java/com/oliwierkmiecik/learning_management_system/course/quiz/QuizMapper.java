package com.oliwierkmiecik.learning_management_system.course.quiz;

import com.oliwierkmiecik.learning_management_system.course.quiz.dto.QuizCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.dto.QuizReadDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.QuizQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class QuizMapper {

    public abstract Quiz quizCreateDTOToQuiz(QuizCreateDTO createDTO);

    @Mapping(source = "questions", target = "questionTexts")
    public abstract QuizReadDTO quizToQuizReadDTO(Quiz quiz);

    public List<QuizReadDTO> quizListToQuizReadDTOList(List<Quiz> quizList) {
        return quizList.stream()
                .map(this::quizToQuizReadDTO)
                .toList();
    }

    protected String mapQuizQuestionToString(QuizQuestion quizQuestion) {
        return quizQuestion.getQuestionText();
    }
}
