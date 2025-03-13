package com.oliwierkmiecik.learning_management_system.course.quiz;

import com.oliwierkmiecik.learning_management_system.course.quiz.dto.QuizCreateDTO;
import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import com.oliwierkmiecik.learning_management_system.exceptions.NullsForbiddenException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    public QuizService(QuizRepository quizRepository, QuizMapper quizMapper) {
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
    }

    public List<Quiz> findAllQuizes() {
        return quizRepository.findAll();
    }

    public Quiz findQuizById(Integer id) {
         return quizRepository.findById(id)
                 .orElseThrow(() -> new NotFoundException("Quiz not found for id: " + id));
    }

    public Quiz saveQuiz(QuizCreateDTO createDTO) {
        checkIfAllFieldsPresent(createDTO);
        Quiz quiz = quizMapper.quizCreateDTOToQuiz(createDTO);

        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Integer id, QuizCreateDTO updates, boolean ifAllFieldsRequired) {
        Quiz updatedQuiz = findQuizById(id);
        Quiz updatesAsEntity = quizMapper.quizCreateDTOToQuiz(updates);

        if (ifAllFieldsRequired) checkIfAllFieldsPresent(updates);

        if (updatesAsEntity.getName() != null) updatedQuiz.setName(updatesAsEntity.getName());
        if (updatesAsEntity.getCourse() != null) updatedQuiz.setCourse(updatesAsEntity.getCourse());
        if (updatesAsEntity.getInCoursePosition() != null) updatedQuiz.setInCoursePosition(updatesAsEntity.getInCoursePosition());

        return quizRepository.save(updatedQuiz);
    }

    public void deleteQuiz(Integer id) {
        quizRepository.delete(findQuizById(id));
    }


    private void checkIfAllFieldsPresent(QuizCreateDTO createDTO) {
        if (createDTO.getCourseId() == null || createDTO.getName() == null || createDTO.getInCoursePosition() == null) {
            throw new NullsForbiddenException("Nulls are forbidden.");
        }
    }
}
