package com.oliwierkmiecik.learning_management_system.course.quiz.question.answer;

import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto.QuizQuestionAnswerCreateDTO;
import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import com.oliwierkmiecik.learning_management_system.exceptions.NullsForbiddenException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizQuestionAnswerService {
    private final QuizQuestionAnswerRepository quizQuestionAnswerRepository;
    private final QuizQuestionAnswerMapper quizQuestionAnswerMapper;

    public QuizQuestionAnswerService(QuizQuestionAnswerRepository quizQuestionAnswerRepository, QuizQuestionAnswerMapper quizQuestionAnswerMapper) {
        this.quizQuestionAnswerRepository = quizQuestionAnswerRepository;
        this.quizQuestionAnswerMapper = quizQuestionAnswerMapper;
    }

    public List<QuizQuestionAnswer> findAllAnswers() {
        return quizQuestionAnswerRepository.findAll();
    }

    public QuizQuestionAnswer findAnswerById(Integer id) {
        return quizQuestionAnswerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Answer with id " + id + " not found."));
    }

    public QuizQuestionAnswer saveAnswer(QuizQuestionAnswerCreateDTO createDTO) {
        QuizQuestionAnswer quizQuestionAnswer = quizQuestionAnswerMapper.QuizQuestionAnswerCreateDTOToQuizQuestionAnswer(createDTO);
        checkIfAllFieldsPresent(quizQuestionAnswer);
        return quizQuestionAnswerRepository.save(quizQuestionAnswer);
    }

    public QuizQuestionAnswer updateAnswer(Integer id, QuizQuestionAnswerCreateDTO updates, boolean ifAllFieldsRequired) {
        QuizQuestionAnswer updatedAnswer = findAnswerById(id);
        QuizQuestionAnswer updatesAsEntity = quizQuestionAnswerMapper.QuizQuestionAnswerCreateDTOToQuizQuestionAnswer(updates);


        if (ifAllFieldsRequired) checkIfAllFieldsPresent(updatesAsEntity);

        if (updatesAsEntity.getAnswerText() != null) updatedAnswer.setAnswerText(updates.getAnswerText());
        if (updatesAsEntity.getQuizQuestion() != null) updatedAnswer.setQuizQuestion(updatesAsEntity.getQuizQuestion());

        return quizQuestionAnswerRepository.save(updatedAnswer);
    }

    public void deleteAnswer(Integer id) {
        quizQuestionAnswerRepository.delete(findAnswerById(id));
    }


    private void checkIfAllFieldsPresent(QuizQuestionAnswer quizQuestionAnswer) {
        if (quizQuestionAnswer.getAnswerText() == null || quizQuestionAnswer.getQuizQuestion() == null) {
            throw new NullsForbiddenException("Nulls are forbidden");
        }
    }
}
