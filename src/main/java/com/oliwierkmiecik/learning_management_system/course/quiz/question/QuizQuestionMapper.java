package com.oliwierkmiecik.learning_management_system.course.quiz.question;

import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.QuizQuestionAnswer;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.dto.QuizQuestionCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.dto.QuizQuestionReadDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class QuizQuestionMapper {

    public abstract QuizQuestion quizQuestionCreateDTOToQuizQuestion(QuizQuestionCreateDTO createDTO);

    @Mapping(source = "answers", target = "answerTexts")
    public abstract QuizQuestionReadDTO quizQuestionToQuizQuestionReadDTO(QuizQuestion entity);

    protected String mapQuizQuestionAnswerToString(QuizQuestionAnswer quizQuestionAnswer) {
        return quizQuestionAnswer.getAnswerText();
    }

    public List<QuizQuestionReadDTO> quizQuestionListToQuizQuestionReadDTOList(List<QuizQuestion> quizQuestionList) {
        return quizQuestionList.stream()
                .map(this::quizQuestionToQuizQuestionReadDTO)
                .toList();
    }
}