package com.oliwierkmiecik.learning_management_system.course;

import com.oliwierkmiecik.learning_management_system.course.dto.CourseCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.lesson.Lesson;
import com.oliwierkmiecik.learning_management_system.course.lesson.LessonService;
import com.oliwierkmiecik.learning_management_system.course.lesson.dto.LessonCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.Quiz;
import com.oliwierkmiecik.learning_management_system.course.quiz.QuizService;
import com.oliwierkmiecik.learning_management_system.course.quiz.dto.QuizCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.QuizQuestion;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.QuizQuestionAnswer;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto.QuizQuestionAnswerCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.dto.QuizQuestionCreateDTO;
import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import com.oliwierkmiecik.learning_management_system.exceptions.NullsForbiddenException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final LessonService lessonService;
    private final QuizService quizService;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper, LessonService lessonService, QuizService quizService) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.lessonService = lessonService;
        this.quizService = quizService;
    }
    //-----------------------------------------------------------------------------------------------------------------
    //                                                 COURSES

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public Course findCourseById(Integer id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found for id: " + id));
    }

    public Course saveCourse(CourseCreateDTO createDTO) {
        checkIfAllFieldsPresent(createDTO);
        Course newCourse = courseMapper.courseCreateDTOToCourse(createDTO);

        return courseRepository.save(newCourse);
    }

    public Course updateCourse(Integer id, CourseCreateDTO updates, boolean ifAllFieldsRequired) {
        Course updatedCourse = findCourseById(id);
        Course updatesAsEntity = courseMapper.courseCreateDTOToCourse(updates);

        if (ifAllFieldsRequired) checkIfAllFieldsPresent(updates);

        if (updatesAsEntity.getName() != null) updatedCourse.setName(updatesAsEntity.getName());
        if (updatesAsEntity.getDescription() != null) updatedCourse.setDescription(updatesAsEntity.getDescription());

        return courseRepository.save(updatedCourse);
    }

    public void deleteCourse(Integer id) {
        courseRepository.delete(findCourseById(id));
    }


    private void checkIfAllFieldsPresent(CourseCreateDTO createDTO) {
        if (createDTO.getName() == null || createDTO.getDescription() == null) {
            throw new NullsForbiddenException("Nulls are forbidden.");
        }
    }
    //-----------------------------------------------------------------------------------------------------------------
    //                                                  LESSONS

    public List<Lesson> findAllLessons() {
        return lessonService.findAllLessons();
    }

    public Lesson findLessonById(Integer lessonId) {
        return lessonService.findLessonById(lessonId);
    }

    public Lesson saveLesson(LessonCreateDTO createDTO) {
        return lessonService.saveLesson(createDTO);
    }

    public Lesson updateLesson(Integer id, LessonCreateDTO updates, boolean ifAllFieldsRequired) {
        return lessonService.updateLesson(id, updates, ifAllFieldsRequired);
    }

    public void deleteLesson(Integer id) {
        lessonService.deleteLesson(id);
    }
    //-----------------------------------------------------------------------------------------------------------------
    //                                                  QUIZES

    public List<Quiz> findAllQuizes() {
        return quizService.findAllQuizes();
    }

    public Quiz findQuizById(Integer quizId) {
        return quizService.findQuizById(quizId);
    }

    public Quiz saveQuiz(QuizCreateDTO createDTO) {
        return quizService.saveQuiz(createDTO);
    }

    public Quiz updateQuiz(Integer id, QuizCreateDTO updates, boolean ifAllFieldsRequired) {
        return quizService.updateQuiz(id, updates, ifAllFieldsRequired);
    }

    public void deleteQuiz(Integer id) {
        quizService.deleteQuiz(id);
    }
    //-----------------------------------------------------------------------------------------------------------------
    //                                               QUIZ QUESTIONS

    public List<QuizQuestion> findAllQuizQuestions() {
        return quizService.findAllQuestions();
    }

    public QuizQuestion findQuizQuestionById(Integer id) {
        return quizService.findQuestionById(id);
    }

    public QuizQuestion saveQuizQuestion(QuizQuestionCreateDTO createDTO) {
        return quizService.saveQuestion(createDTO);
    }

    public QuizQuestion updateQuizQuestion(Integer id, QuizQuestionCreateDTO updates, boolean ifAllFieldsRequired) {
        return quizService.updateQuestion(id, updates, ifAllFieldsRequired);
    }

    public void deleteQuizQuestion(Integer id) {
        quizService.deleteQuestion(id);
    }

    //-----------------------------------------------------------------------------------------------------------------
    //                                               QUIZ QUESTION ANSWERS

    public List<QuizQuestionAnswer> findAllQuizQuestionAnswers() {
        return quizService.findAllQuestionAnswers();
    }

    public QuizQuestionAnswer findQuizQuestionAnswerById(Integer id) {
        return quizService.findQuestionAnswerById(id);
    }

    public QuizQuestionAnswer saveQuizQuestionAnswer(QuizQuestionAnswerCreateDTO createDTO) {
        return quizService.saveQuestionAnswer(createDTO);
    }

    public QuizQuestionAnswer updateQuizQuestionAnswer(Integer id, QuizQuestionAnswerCreateDTO updates, boolean ifAllFieldsRequired) {
        return quizService.updateQuestionAnswer(id, updates, ifAllFieldsRequired);
    }

    public void deleteQuizQuestionAnswer(Integer id) {
        quizService.deleteQuestionAnswer(id);
    }
}
