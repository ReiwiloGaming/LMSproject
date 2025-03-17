package com.oliwierkmiecik.learning_management_system.course;

import com.oliwierkmiecik.learning_management_system.course.dto.CourseCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.lesson.Lesson;
import com.oliwierkmiecik.learning_management_system.course.lesson.LessonMapper;
import com.oliwierkmiecik.learning_management_system.course.lesson.dto.LessonCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.Quiz;
import com.oliwierkmiecik.learning_management_system.course.quiz.QuizMapper;
import com.oliwierkmiecik.learning_management_system.course.quiz.dto.QuizCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.QuizQuestion;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.QuizQuestionMapper;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.QuizQuestionAnswer;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.QuizQuestionAnswerMapper;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto.QuizQuestionAnswerCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.dto.QuizQuestionCreateDTO;
import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final LessonMapper lessonMapper;
    private final QuizMapper quizMapper;
    private final QuizQuestionMapper quizQuestionMapper;
    private final QuizQuestionAnswerMapper quizQuestionAnswerMapper;

    private final static Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper, LessonMapper lessonMapper, QuizMapper quizMapper, QuizQuestionMapper quizQuestionMapper, QuizQuestionAnswerMapper quizQuestionAnswerMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.lessonMapper = lessonMapper;
        this.quizMapper = quizMapper;
        this.quizQuestionMapper = quizQuestionMapper;
        this.quizQuestionAnswerMapper = quizQuestionAnswerMapper;
    }

    public List<Course> findAllCourses() {
        LOGGER.info("Downloaded all courses");
        return courseRepository.findAll();
    }

    public Course findCourseById(Integer courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found for id: " + courseId));
    }

    public List<Lesson> findAllCourseLessons(Integer courseId) {
        return findCourseById(courseId).getLessons();
    }

    public Lesson findLessonById(Integer courseId, Integer lessonId) {
        List<Lesson> allCourseLessons = findAllCourseLessons(courseId);
        return allCourseLessons.stream()
                .filter(lesson -> lesson.getId().equals(lessonId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Lesson not found for id " + lessonId + " in course with id " + courseId));
    }

    public List<Quiz> findAllCourseQuizes(Integer courseId) {
        return findCourseById(courseId).getQuizes();
    }

    public Quiz findQuizById(Integer courseId, Integer quizId) {
        List<Quiz> allCourseQuizes = findAllCourseQuizes(courseId);
        return allCourseQuizes.stream()
                .filter(quiz -> quiz.getId().equals(quizId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Quiz not found for id " + quizId + " in course with id " + courseId));
    }

    public List<QuizQuestion> findAllQuizQuestions(Integer courseId, Integer quizId) {
        Quiz quiz = findQuizById(courseId, quizId);
        return quiz.getQuestions();
    }

    public QuizQuestion findQuizQuestionById(Integer courseId, Integer quizId, Integer questionId) {
        List<QuizQuestion> allQuizQuestions = findAllQuizQuestions(courseId, quizId);
        return allQuizQuestions.stream()
                .filter(quizQuestion -> quizQuestion.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Question not found for id " + questionId + " in quiz with id " + quizId));
    }

    public List<QuizQuestionAnswer> findAllQuizQuestionAnswers(Integer courseId, Integer quizId, Integer questionId) {
        QuizQuestion quizQuestion = findQuizQuestionById(courseId, quizId, questionId);
        return quizQuestion.getAnswers();
    }

    public QuizQuestionAnswer findQuizQuestionAnswerById(Integer courseId, Integer quizId, Integer questionId, Integer answerId) {
        List<QuizQuestionAnswer> allQuizQuestionAnswers = findAllQuizQuestionAnswers(courseId, quizId, questionId);
        return allQuizQuestionAnswers.stream()
                .filter(quizQuestionAnswer -> quizQuestionAnswer.getId().equals(answerId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Answer not found for id " + answerId + " in question with id " + questionId));
    }

    public Course saveCourse(CourseCreateDTO createDTO) {
        createDTO.checkIfAllFieldsPresent();
        Course newCourse = courseMapper.courseCreateDTOToCourse(createDTO);

        return courseRepository.save(newCourse);
    }

    public Lesson addLessonToCourse(Integer courseId, LessonCreateDTO lessonCreateDTO) {
        lessonCreateDTO.checkIfAllFieldsPresent();

        Course course = findCourseById(courseId);
        Lesson newLesson = lessonMapper.lessonCreateDTOToLesson(lessonCreateDTO);

        course.addLesson(newLesson);
        Course savedCourse = courseRepository.save(course);

        return savedCourse.getLessons().getLast();
    }

    public Quiz addQuizToCourse(Integer courseId, QuizCreateDTO quizCreateDTO) {
        quizCreateDTO.checkIfAllFieldsPresent();

        Course course = findCourseById(courseId);
        Quiz newQuiz = quizMapper.quizCreateDTOToQuiz(quizCreateDTO);

        course.addQuiz(newQuiz);
        Course savedCourse = courseRepository.save(course);

        return savedCourse.getQuizes().getLast();
    }

    public QuizQuestion addQuestionToQuiz(Integer courseId, Integer quizId, QuizQuestionCreateDTO quizQuestionCreateDTO) {
        quizQuestionCreateDTO.checkIfAllFieldsPresent();

        Course course = findCourseById(courseId);
        Quiz quiz = findQuizById(courseId, quizId);
        QuizQuestion newQuizQuestion = quizQuestionMapper.quizQuestionCreateDTOToQuizQuestion(quizQuestionCreateDTO);

        quiz.addQuestion(newQuizQuestion);

        courseRepository.save(course);
        return findAllQuizQuestions(courseId, quizId).getLast();
    }

    public QuizQuestionAnswer addAnswerToQuestion(Integer courseId, Integer quizId, Integer questionId, QuizQuestionAnswerCreateDTO quizQuestionAnswerCreateDTO) {
        quizQuestionAnswerCreateDTO.checkIfAllFieldsPresent();

        Course course = findCourseById(courseId);
        QuizQuestion question = findQuizQuestionById(courseId, quizId, questionId);
        QuizQuestionAnswer newAnswer = quizQuestionAnswerMapper.QuizQuestionAnswerCreateDTOToQuizQuestionAnswer(quizQuestionAnswerCreateDTO);

        question.addAnswer(newAnswer);
        courseRepository.save(course);
        return findAllQuizQuestionAnswers(courseId, quizId, questionId).getLast();
    }

    public Course updateCourse(Integer courseId, CourseCreateDTO updates, boolean ifAllFieldsRequired) {
        Course updatedCourse = findCourseById(courseId);
        Course updatesAsEntity = courseMapper.courseCreateDTOToCourse(updates);

        if (ifAllFieldsRequired) updates.checkIfAllFieldsPresent();

        if (updatesAsEntity.getName() != null) updatedCourse.setName(updatesAsEntity.getName());
        if (updatesAsEntity.getDescription() != null) updatedCourse.setDescription(updatesAsEntity.getDescription());

        return courseRepository.save(updatedCourse);
    }

    public Lesson updateLessonInCourse(Integer courseId, Integer lessonId, LessonCreateDTO updates, boolean ifAllFieldsRequired) {
        if (ifAllFieldsRequired) updates.checkIfAllFieldsPresent();

        Course course = findCourseById(courseId);
        Lesson updatedLesson = findLessonById(courseId, lessonId);

        if (updates.getName() != null) updatedLesson.setName(updates.getName());
        if (updates.getContent() != null) updatedLesson.setContent(updates.getContent());
        if (updates.getInCoursePosition() != null) updatedLesson.setInCoursePosition(updates.getInCoursePosition());

        courseRepository.save(course);
        return updatedLesson;
    }

    public Quiz updateQuizInCourse(Integer courseId, Integer quizId, QuizCreateDTO updates, boolean ifAllFieldsRequired) {
        if (ifAllFieldsRequired) updates.checkIfAllFieldsPresent();

        Course course = findCourseById(courseId);
        Quiz updatedQuiz = findQuizById(courseId, quizId);

        if (updates.getName() != null) updatedQuiz.setName(updates.getName());
        if (updates.getInCoursePosition() != null) updatedQuiz.setInCoursePosition(updates.getInCoursePosition());

        courseRepository.save(course);
        return updatedQuiz;
    }

    public QuizQuestion updateQuestionInQuiz(Integer courseId, Integer quizId, Integer questionId, QuizQuestionCreateDTO updates, boolean ifAllFieldsRequired) {
        if (ifAllFieldsRequired) updates.checkIfAllFieldsPresent();

        Course course = findCourseById(courseId);
        QuizQuestion updatedQuestion = findQuizQuestionById(courseId, quizId, questionId);

        if (updates.getQuestionText() != null) updatedQuestion.setQuestionText(updates.getQuestionText());
        if (updates.getCorrectAnswer() != null) updatedQuestion.setCorrectAnswer(updates.getCorrectAnswer());

        courseRepository.save(course);
        return updatedQuestion;
    }

    public QuizQuestionAnswer updateQuestionAnswer(Integer courseId, Integer quizId, Integer questionId, Integer answerId, QuizQuestionAnswerCreateDTO updates, boolean ifAllFieldsRequired) {
        if (ifAllFieldsRequired) updates.checkIfAllFieldsPresent();

        Course course = findCourseById(courseId);
        QuizQuestionAnswer updatedAnswer = findQuizQuestionAnswerById(courseId, quizId, questionId, answerId);

        if (updates.getAnswerText() != null) updatedAnswer.setAnswerText(updates.getAnswerText());

        courseRepository.save(course);
        return updatedAnswer;
    }

    public void deleteCourse(Integer courseId) {
        courseRepository.delete(findCourseById(courseId));
    }

    public void deleteLesson(Integer courseId, Integer lessonId) {
        Course course = findCourseById(courseId);
        course.removeLesson(findLessonById(courseId, lessonId));
        courseRepository.save(course);
    }

    public void deleteQuiz(Integer courseId, Integer quizId) {
        Course course = findCourseById(courseId);
        course.removeQuiz(findQuizById(courseId, quizId));
        courseRepository.save(course);
    }

    public void deleteQuizQuestion(Integer courseId, Integer quizId, Integer questionId) {
        Course course = findCourseById(courseId);
        Quiz quiz = findQuizById(courseId, quizId);
        quiz.removeQuestion(findQuizQuestionById(courseId, quizId, questionId));
        courseRepository.save(course);
    }

    public void deleteQuizQuestionAnswer(Integer courseId, Integer quizId, Integer questionId, Integer answerId) {
        Course course = findCourseById(courseId);
        QuizQuestion quizQuestion = findQuizQuestionById(courseId, quizId, questionId);
        quizQuestion.removeAnswer(findQuizQuestionAnswerById(courseId, quizId, questionId, answerId));
        courseRepository.save(course);
    }
}
