package com.oliwierkmiecik.learning_management_system.course.quiz.question.answer;

import com.oliwierkmiecik.learning_management_system.course.quiz.question.QuizQuestion;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.QuizQuestionRepository;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto.QuizQuestionAnswerCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto.QuizQuestionAnswerReadDTO;
import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class QuizQuestionAnswerMapper{
    @Autowired
    protected QuizQuestionRepository quizQuestionRepository;

    protected abstract QuizQuestionAnswerReadDTO QuizQuestionAnswerToQuizQuestionAnswerReadDTO(QuizQuestionAnswer quizQuestionAnswer);

    @Mapping(source = "quizQuestionId", target = "quizQuestion")
    protected abstract QuizQuestionAnswer QuizQuestionAnswerCreateDTOToQuizQuestionAnswer(QuizQuestionAnswerCreateDTO createDTO);

    protected QuizQuestion mapQuizQuestion(Integer quizQuestionId) {
        if (quizQuestionId== null) return null;
        return quizQuestionRepository.findById(quizQuestionId)
                .orElseThrow(() -> new NotFoundException("Quiz question not found for id: " + quizQuestionId));
    }

    protected List<QuizQuestionAnswerReadDTO> entityListToReadDTOList(List<QuizQuestionAnswer> entityList) {
        return entityList.stream().map(this::QuizQuestionAnswerToQuizQuestionAnswerReadDTO).toList();
    }
}
