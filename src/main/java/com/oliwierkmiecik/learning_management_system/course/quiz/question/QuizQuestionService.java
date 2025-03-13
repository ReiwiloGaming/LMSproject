package com.oliwierkmiecik.learning_management_system.course.quiz.question;

import com.oliwierkmiecik.learning_management_system.course.quiz.question.dto.QuizQuestionCreateDTO;
import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import com.oliwierkmiecik.learning_management_system.exceptions.NullsForbiddenException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizQuestionService {
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizQuestionMapper quizQuestionMapper;

    public QuizQuestionService(QuizQuestionRepository quizQuestionRepository, QuizQuestionMapper quizQuestionMapper) {
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizQuestionMapper = quizQuestionMapper;
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
}
