package com.oliwierkmiecik.learning_management_system.course.quiz.question;

import com.oliwierkmiecik.learning_management_system.course.quiz.Quiz;
import com.oliwierkmiecik.learning_management_system.course.quiz.QuizRepository;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.QuizQuestionAnswer;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.dto.QuizQuestionCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.dto.QuizQuestionReadDTO;
import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class QuizQuestionMapper {
    @Autowired
    protected QuizRepository quizRepository;

    @Mapping(source = "quizId", target = "quiz")
    protected abstract QuizQuestion quizQuestionCreateDTOToQuizQuestion(QuizQuestionCreateDTO createDTO);

    @Mapping(source = "answers", target = "answerTexts")
    public abstract QuizQuestionReadDTO QuizQuestionToQuizQuestionReadDTO(QuizQuestion entity);

    protected String mapQuizQuestionAnswerToString(QuizQuestionAnswer quizQuestionAnswer) {
        return quizQuestionAnswer.getAnswerText();
    }

    protected Quiz mapQuiz(Integer quizId) {
        if (quizId== null) return null;
        return quizRepository.findById(quizId)
                .orElseThrow(() -> new NotFoundException("Quiz not found for id: " + quizId));
    }

    public List<QuizQuestionReadDTO> EntityListToReadDTOList(List<QuizQuestion> entityList) {
        return entityList.stream()
                .map(this::QuizQuestionToQuizQuestionReadDTO)
                .toList();
    }
}