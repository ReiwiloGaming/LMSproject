package com.oliwierkmiecik.learning_management_system.course.quiz.question;

import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.QuizQuestionAnswer;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.QuizQuestionAnswerService;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto.QuizQuestionAnswerCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.dto.QuizQuestionCreateDTO;
import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import com.oliwierkmiecik.learning_management_system.exceptions.NullsForbiddenException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizQuestionService {
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizQuestionMapper quizQuestionMapper;
    private final QuizQuestionAnswerService quizQuestionAnswerService;

    public QuizQuestionService(QuizQuestionRepository quizQuestionRepository, QuizQuestionMapper quizQuestionMapper, QuizQuestionAnswerService quizQuestionAnswerService) {
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizQuestionMapper = quizQuestionMapper;
        this.quizQuestionAnswerService = quizQuestionAnswerService;
    }

    // Kolejne zadanie: stworzyć serwis i kontroler dla QuizQuestion

    public List<QuizQuestion> findAllQuestions() {
        return quizQuestionRepository.findAll();
    }

    public QuizQuestion findQuestionById(Integer id) {
        return quizQuestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Question not found for id: " + id));
    }

    public QuizQuestion saveQuestion(QuizQuestionCreateDTO createDTO) {
        QuizQuestion newQuizQuestion = quizQuestionMapper.quizQuestionCreateDTOToQuizQuestion(createDTO);
        checkIfAllFieldsPresent(newQuizQuestion);
        return quizQuestionRepository.save(newQuizQuestion);
    }

    public QuizQuestion updateQuestion(Integer id, QuizQuestionCreateDTO updates, boolean ifAllFieldsRequired) {
        QuizQuestion updatedQuestion = findQuestionById(id);
        QuizQuestion updatesAsEntity = quizQuestionMapper.quizQuestionCreateDTOToQuizQuestion(updates);

        if (ifAllFieldsRequired) checkIfAllFieldsPresent(updatesAsEntity);

        if (updatesAsEntity.getQuestionText() != null) updatedQuestion.setQuestionText(updatesAsEntity.getQuestionText());
        if (updatesAsEntity.getQuiz() != null) updatedQuestion.setQuiz(updatesAsEntity.getQuiz());
        if (updatesAsEntity.getCorrectAnswer() != null) updatedQuestion.setCorrectAnswer(updatesAsEntity.getCorrectAnswer());

        return quizQuestionRepository.save(updatedQuestion);
    }

    public void deleteQuestion(Integer id) {
        quizQuestionRepository.delete(findQuestionById(id));
    }


    private void checkIfAllFieldsPresent(QuizQuestion quizQuestion) {
        if (quizQuestion.getQuestionText() == null || quizQuestion.getQuiz() == null || quizQuestion.getCorrectAnswer() == null) {
            throw new NullsForbiddenException("Nulls are forbidden");
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    //                                               QUIZ QUESTION ANSWERS

    public List<QuizQuestionAnswer> findAllAnswers() {
        return quizQuestionAnswerService.findAllAnswers();
    }

    public QuizQuestionAnswer findAnswerById(Integer id) {
        return quizQuestionAnswerService.findAnswerById(id);
    }

    public QuizQuestionAnswer saveAnswer(QuizQuestionAnswerCreateDTO createDTO) {
        return quizQuestionAnswerService.saveAnswer(createDTO);
    }

    public QuizQuestionAnswer updateAnswer(Integer id, QuizQuestionAnswerCreateDTO updates, boolean ifAllFieldsRequired) {
        return quizQuestionAnswerService.updateAnswer(id, updates, ifAllFieldsRequired);
    }

    public void deleteAnswer(Integer id) {
        quizQuestionAnswerService.deleteAnswer(id);
    }
}
