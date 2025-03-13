package com.oliwierkmiecik.learning_management_system.course.quiz;

import com.oliwierkmiecik.learning_management_system.course.Course;
import com.oliwierkmiecik.learning_management_system.course.CourseRepository;
import com.oliwierkmiecik.learning_management_system.course.quiz.dto.QuizCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.dto.QuizReadDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.QuizQuestion;
import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class QuizMapper {
    @Autowired
    protected CourseRepository courseRepository;

    @Mapping(source = "courseId", target = "course")
    protected abstract Quiz quizCreateDTOToQuiz(QuizCreateDTO createDTO);

    @Mapping(source = "questions", target = "questionTexts")
    protected abstract QuizReadDTO quizToQuizReadDTO(Quiz quiz);

    protected Course mapCourse(Integer courseId) {
        if (courseId== null) return null;
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found for id: " + courseId));
    }

    protected String mapQuizQuestionToString(QuizQuestion quizQuestion) {
        return quizQuestion.getQuestionText();
    }

    protected List<QuizReadDTO> QuizListToQuizReadDTOList(List<Quiz> quizList) {
        return quizList.stream()
                .map(this::quizToQuizReadDTO)
                .toList();
    }
}
