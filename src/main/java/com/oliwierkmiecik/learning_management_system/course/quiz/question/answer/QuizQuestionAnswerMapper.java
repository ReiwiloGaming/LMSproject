package com.oliwierkmiecik.learning_management_system.course.quiz.question.answer;

import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto.QuizQuestionAnswerCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto.QuizQuestionAnswerReadDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class QuizQuestionAnswerMapper{

    public abstract QuizQuestionAnswerReadDTO QuizQuestionAnswerToQuizQuestionAnswerReadDTO(QuizQuestionAnswer quizQuestionAnswer);

    public abstract QuizQuestionAnswer QuizQuestionAnswerCreateDTOToQuizQuestionAnswer(QuizQuestionAnswerCreateDTO createDTO);

    public List<QuizQuestionAnswerReadDTO> entityListToReadDTOList(List<QuizQuestionAnswer> entityList) {
        return entityList.stream().map(this::QuizQuestionAnswerToQuizQuestionAnswerReadDTO).toList();
    }
}
