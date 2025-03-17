package com.oliwierkmiecik.learning_management_system.course;

import com.oliwierkmiecik.learning_management_system.course.dto.CourseCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.dto.CourseReadDTO;
import com.oliwierkmiecik.learning_management_system.course.lesson.Lesson;
import com.oliwierkmiecik.learning_management_system.course.lesson.LessonMapper;
import com.oliwierkmiecik.learning_management_system.course.lesson.dto.LessonCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.lesson.dto.LessonReadDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.Quiz;
import com.oliwierkmiecik.learning_management_system.course.quiz.QuizMapper;
import com.oliwierkmiecik.learning_management_system.course.quiz.dto.QuizCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.dto.QuizReadDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.QuizQuestion;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.QuizQuestionMapper;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.QuizQuestionAnswer;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.QuizQuestionAnswerMapper;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto.QuizQuestionAnswerCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto.QuizQuestionAnswerReadDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.dto.QuizQuestionCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.dto.QuizQuestionReadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final LessonMapper lessonMapper;
    private final QuizMapper quizMapper;
    private final QuizQuestionMapper quizQuestionMapper;
    private final QuizQuestionAnswerMapper quizQuestionAnswerMapper;

    public CourseController(CourseService courseService, CourseMapper courseMapper, LessonMapper lessonMapper,
                            QuizMapper quizMapper, QuizQuestionMapper quizQuestionMapper,
                            QuizQuestionAnswerMapper quizQuestionAnswerMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
        this.lessonMapper = lessonMapper;
        this.quizMapper = quizMapper;
        this.quizQuestionMapper = quizQuestionMapper;
        this.quizQuestionAnswerMapper = quizQuestionAnswerMapper;
    }
    //-----------------------------------------------------------------------------------------------------------------
    //                                               COURSES

    @GetMapping("courses")
    public ResponseEntity<List<CourseReadDTO>> getAllCourses() {
        List<Course> allCourses = courseService.findAllCourses();
        List<CourseReadDTO> courseReadDTOS = courseMapper.courseListToCourseReadDTOList(allCourses);

        return ResponseEntity.ok(courseReadDTOS);
    }

    @GetMapping("courses/{id}")
    public ResponseEntity<CourseReadDTO> getCourseById(@PathVariable Integer id) {
        Course courseById = courseService.findCourseById(id);
        CourseReadDTO courseReadDTO = courseMapper.courseToCourseReadDTO(courseById);

        return ResponseEntity.ok(courseReadDTO);
    }

    @PostMapping("courses")
    public ResponseEntity<CourseReadDTO> createCourse(@RequestBody CourseCreateDTO createDTO) {
        Course newCourse = courseService.saveCourse(createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCourse.getId())
                .toUri();

        CourseReadDTO courseReadDTO = courseMapper.courseToCourseReadDTO(newCourse);

        return ResponseEntity.created(location).body(courseReadDTO);
    }

    @PutMapping("courses/{id}")
    public ResponseEntity<CourseReadDTO> updateCourse(@PathVariable Integer id, @RequestBody CourseCreateDTO updates) {
        Course updatedCourse = courseService.updateCourse(id, updates, true);

        CourseReadDTO courseReadDTO = courseMapper.courseToCourseReadDTO(updatedCourse);

        return ResponseEntity.ok(courseReadDTO);
    }

    @PatchMapping("courses/{id}")
    public ResponseEntity<CourseReadDTO> partiallyUpdateCourse(@PathVariable Integer id, @RequestBody CourseCreateDTO updates) {
        Course updatedCourse = courseService.updateCourse(id, updates, false);

        CourseReadDTO courseReadDTO = courseMapper.courseToCourseReadDTO(updatedCourse);

        return ResponseEntity.ok(courseReadDTO);
    }

    @DeleteMapping("courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    //-----------------------------------------------------------------------------------------------------------------
    //                                                 LESSONS

    @GetMapping("courses/{courseId}/lessons")
    public ResponseEntity<List<LessonReadDTO>> getAllCourseLessons(@PathVariable Integer courseId) {
        List<Lesson> allCourseLessons = courseService.findAllCourseLessons(courseId);
        List<LessonReadDTO> lessonReadDTOS = lessonMapper.lessonListToLessonReadDTOList(allCourseLessons);

        return ResponseEntity.ok(lessonReadDTOS);
    }

    @GetMapping("courses/{courseId}/lessons/{lessonId}")
    public ResponseEntity<LessonReadDTO> getLessonById(@PathVariable Integer courseId, @PathVariable Integer lessonId) {
        Lesson lessonById = courseService.findLessonById(courseId, lessonId);
        LessonReadDTO lessonReadDTO = lessonMapper.lessonToLessonReadDTO(lessonById);

        return ResponseEntity.ok(lessonReadDTO);
    }

    @PostMapping("courses/{courseId}/lessons")
    public ResponseEntity<LessonReadDTO> createLesson(@PathVariable Integer courseId, @RequestBody LessonCreateDTO createDTO) {
        Lesson newLesson = courseService.addLessonToCourse(courseId, createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLesson.getId())
                .toUri();

        LessonReadDTO lessonReadDTO = lessonMapper.lessonToLessonReadDTO(newLesson);

        return ResponseEntity.created(location).body(lessonReadDTO);
    }

    @PutMapping("courses/{courseId}/lessons/{lessonId}")
    public ResponseEntity<LessonReadDTO> updateLesson(@PathVariable Integer courseId, @PathVariable Integer lessonId, @RequestBody LessonCreateDTO updates) {
        Lesson updatedLesson = courseService.updateLessonInCourse(courseId, lessonId, updates, true);
        LessonReadDTO lessonReadDTO = lessonMapper.lessonToLessonReadDTO(updatedLesson);

        return ResponseEntity.ok(lessonReadDTO);
    }

    @PatchMapping("courses/{courseId}/lessons/{lessonId}")
    public ResponseEntity<LessonReadDTO> partiallyUpdateLesson(@PathVariable Integer courseId, @PathVariable Integer lessonId, @RequestBody LessonCreateDTO updates) {
        Lesson updatedLesson = courseService.updateLessonInCourse(courseId, lessonId, updates, false);
        LessonReadDTO lessonReadDTO = lessonMapper.lessonToLessonReadDTO(updatedLesson);

        return ResponseEntity.ok(lessonReadDTO);
    }

    @DeleteMapping("courses/{courseId}/lessons/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Integer courseId, @PathVariable Integer lessonId) {
        courseService.deleteLesson(courseId, lessonId);
        return ResponseEntity.noContent().build();
    }
    //-----------------------------------------------------------------------------------------------------------------
    //                                                 QUIZES

    @GetMapping("courses/{courseId}/quizes")
    public ResponseEntity<List<QuizReadDTO>> getAllCourseQuizes(@PathVariable Integer courseId) {
        List<Quiz> allCourseQuizes = courseService.findAllCourseQuizes(courseId);
        List<QuizReadDTO> quizReadDTOS = quizMapper.quizListToQuizReadDTOList(allCourseQuizes);

        return ResponseEntity.ok(quizReadDTOS);
    }

    @GetMapping("courses/{courseId}/quizes/{quizId}")
    public ResponseEntity<QuizReadDTO> getQuizById(@PathVariable Integer courseId, @PathVariable Integer quizId) {
        Quiz quizById = courseService.findQuizById(courseId, quizId);
        QuizReadDTO quizReadDTO = quizMapper.quizToQuizReadDTO(quizById);

        return ResponseEntity.ok(quizReadDTO);
    }

    @PostMapping("courses/{courseId}/quizes")
    public ResponseEntity<QuizReadDTO> createQuiz(@PathVariable Integer courseId, @RequestBody QuizCreateDTO createDTO) {
        Quiz newQuiz = courseService.addQuizToCourse(courseId, createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newQuiz.getId())
                .toUri();

        QuizReadDTO quizReadDTO = quizMapper.quizToQuizReadDTO(newQuiz);

        return ResponseEntity.created(location).body(quizReadDTO);
    }

    @PutMapping("courses/{courseId}/quizes/{quizId}")
    public ResponseEntity<QuizReadDTO> updateQuiz(@PathVariable Integer courseId, @PathVariable Integer quizId, @RequestBody QuizCreateDTO updates) {
        Quiz quiz = courseService.updateQuizInCourse(courseId, quizId, updates, true);
        QuizReadDTO quizReadDTO = quizMapper.quizToQuizReadDTO(quiz);

        return ResponseEntity.ok(quizReadDTO);
    }

    @PatchMapping("courses/{courseId}/quizes/{quizId}")
    public ResponseEntity<QuizReadDTO> partiallyUpdateQuiz(@PathVariable Integer courseId, @PathVariable Integer quizId, @RequestBody QuizCreateDTO updates) {
        Quiz quiz = courseService.updateQuizInCourse(courseId, quizId, updates, false);
        QuizReadDTO quizReadDTO = quizMapper.quizToQuizReadDTO(quiz);

        return ResponseEntity.ok(quizReadDTO);
    }

    @DeleteMapping("courses/{courseId}/quizes/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Integer courseId, @PathVariable Integer quizId) {
        courseService.deleteQuiz(courseId, quizId);
        return ResponseEntity.noContent().build();
    }
    //-----------------------------------------------------------------------------------------------------------------
    //                                              QUIZ QUESTIONS

    @GetMapping("courses/{courseId}/quizes/{quizId}/questions")
    public ResponseEntity<List<QuizQuestionReadDTO>> getAllQuizQuestions(@PathVariable Integer courseId, @PathVariable Integer quizId) {
        List<QuizQuestion> allQuizQuestions = courseService.findAllQuizQuestions(courseId, quizId);
        List<QuizQuestionReadDTO> quizQuestionReadDTOS = quizQuestionMapper.quizQuestionListToQuizQuestionReadDTOList(allQuizQuestions);

        return ResponseEntity.ok(quizQuestionReadDTOS);
    }

    @GetMapping("courses/{courseId}/quizes/{quizId}/questions/{questionId}")
    public ResponseEntity<QuizQuestionReadDTO> getQuizQuestionById(@PathVariable Integer courseId, @PathVariable Integer quizId, @PathVariable Integer questionId) {
        QuizQuestion quizQuestion = courseService.findQuizQuestionById(courseId, quizId, questionId);
        QuizQuestionReadDTO quizQuestionReadDTO = quizQuestionMapper.quizQuestionToQuizQuestionReadDTO(quizQuestion);

        return ResponseEntity.ok(quizQuestionReadDTO);
    }

    @PostMapping("courses/{courseId}/quizes/{quizId}/questions")
    public ResponseEntity<QuizQuestionReadDTO> createQuizQuestion(@PathVariable Integer courseId, @PathVariable Integer quizId, @RequestBody QuizQuestionCreateDTO createDTO) {
        QuizQuestion newQuestion = courseService.addQuestionToQuiz(courseId, quizId, createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newQuestion.getId())
                .toUri();

        QuizQuestionReadDTO quizQuestionReadDTO = quizQuestionMapper.quizQuestionToQuizQuestionReadDTO(newQuestion);

        return ResponseEntity.created(location).body(quizQuestionReadDTO);
    }

    @PutMapping("courses/{courseId}/quizes/{quizId}/questions/{questionId}")
    public ResponseEntity<QuizQuestionReadDTO> updateQuizQuestion(@PathVariable Integer courseId, @PathVariable Integer quizId, @PathVariable Integer questionId, @RequestBody QuizQuestionCreateDTO updates) {
        QuizQuestion updatedQuestion = courseService.updateQuestionInQuiz(courseId, quizId, questionId, updates, true);
        QuizQuestionReadDTO quizQuestionReadDTO = quizQuestionMapper.quizQuestionToQuizQuestionReadDTO(updatedQuestion);

        return ResponseEntity.ok(quizQuestionReadDTO);
    }

    @PatchMapping("courses/{courseId}/quizes/{quizId}/questions/{questionId}")
    public ResponseEntity<QuizQuestionReadDTO> partiallyUpdateQuizQuestion(@PathVariable Integer courseId, @PathVariable Integer quizId, @PathVariable Integer questionId, @RequestBody QuizQuestionCreateDTO updates) {
        QuizQuestion updatedQuestion = courseService.updateQuestionInQuiz(courseId, quizId, questionId, updates, false);
        QuizQuestionReadDTO quizQuestionReadDTO = quizQuestionMapper.quizQuestionToQuizQuestionReadDTO(updatedQuestion);

        return ResponseEntity.ok(quizQuestionReadDTO);
    }

    @DeleteMapping("courses/{courseId}/quizes/{quizId}/questions/{questionId}")
    public ResponseEntity<Void> deleteQuizQuestion(@PathVariable Integer courseId, @PathVariable Integer quizId, @PathVariable Integer questionId) {
        courseService.deleteQuizQuestion(courseId, quizId, questionId);
        return ResponseEntity.noContent().build();
    }
    //-----------------------------------------------------------------------------------------------------------------
    //                                           QUIZ QUESTION ANSWERS

    @GetMapping("courses/{courseId}/quizes/{quizId}/questions/{questionId}/answers")
    public ResponseEntity<List<QuizQuestionAnswerReadDTO>> getAllQuestionAnswers(@PathVariable Integer courseId, @PathVariable Integer quizId, @PathVariable Integer questionId) {
        List<QuizQuestionAnswer> allQuizQuestionAnswers = courseService.findAllQuizQuestionAnswers(courseId, quizId, questionId);
        List<QuizQuestionAnswerReadDTO> quizQuestionAnswerReadDTOS = quizQuestionAnswerMapper.entityListToReadDTOList(allQuizQuestionAnswers);

        return ResponseEntity.ok(quizQuestionAnswerReadDTOS);
    }

    @GetMapping("courses/{courseId}/quizes/{quizId}/questions/{questionId}/answers/{answerId}")
    public ResponseEntity<QuizQuestionAnswerReadDTO> getQuestionAnswerById(@PathVariable Integer courseId, @PathVariable Integer quizId, @PathVariable Integer questionId, @PathVariable Integer answerId) {
        QuizQuestionAnswer quizQuestionAnswerById = courseService.findQuizQuestionAnswerById(courseId, quizId, questionId, answerId);
        QuizQuestionAnswerReadDTO answerReadDTO = quizQuestionAnswerMapper.QuizQuestionAnswerToQuizQuestionAnswerReadDTO(quizQuestionAnswerById);

        return ResponseEntity.ok(answerReadDTO);
    }

    @PostMapping("courses/{courseId}/quizes/{quizId}/questions/{questionId}/answers")
    public ResponseEntity<QuizQuestionAnswerReadDTO> createQuestionAnswer(@PathVariable Integer courseId, @PathVariable Integer quizId, @PathVariable Integer questionId, @RequestBody QuizQuestionAnswerCreateDTO createDTO) {
        QuizQuestionAnswer newQuizQuestionAnswer = courseService.addAnswerToQuestion(courseId, quizId, questionId, createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newQuizQuestionAnswer.getId())
                .toUri();

        QuizQuestionAnswerReadDTO answerReadDTO = quizQuestionAnswerMapper.QuizQuestionAnswerToQuizQuestionAnswerReadDTO(newQuizQuestionAnswer);

        return ResponseEntity.created(location).body(answerReadDTO);
    }

    @PutMapping("courses/{courseId}/quizes/{quizId}/questions/{questionId}/answers/{answerId}")
    public ResponseEntity<QuizQuestionAnswerReadDTO> updateQuestionAnswer(@PathVariable Integer courseId, @PathVariable Integer quizId, @PathVariable Integer questionId, @PathVariable Integer answerId, @RequestBody QuizQuestionAnswerCreateDTO updates) {
        QuizQuestionAnswer updatedQuestionAnswer = courseService.updateQuestionAnswer(courseId, quizId, questionId, answerId, updates, true);
        QuizQuestionAnswerReadDTO answerReadDTO = quizQuestionAnswerMapper.QuizQuestionAnswerToQuizQuestionAnswerReadDTO(updatedQuestionAnswer);

        return ResponseEntity.ok(answerReadDTO);
    }

    @PatchMapping("courses/{courseId}/quizes/{quizId}/questions/{questionId}/answers/{answerId}")
    public ResponseEntity<QuizQuestionAnswerReadDTO> partiallyUpdateQuestionAnswer(@PathVariable Integer courseId, @PathVariable Integer quizId, @PathVariable Integer questionId, @PathVariable Integer answerId, @RequestBody QuizQuestionAnswerCreateDTO updates) {
        QuizQuestionAnswer updatedQuestionAnswer = courseService.updateQuestionAnswer(courseId, quizId, questionId, answerId, updates, false);
        QuizQuestionAnswerReadDTO answerReadDTO = quizQuestionAnswerMapper.QuizQuestionAnswerToQuizQuestionAnswerReadDTO(updatedQuestionAnswer);

        return ResponseEntity.ok(answerReadDTO);
    }

    @DeleteMapping("courses/{courseId}/quizes/{quizId}/questions/{questionId}/answers/{answerId}")
    public ResponseEntity<Void> deleteQuestionAnswer(@PathVariable Integer courseId, @PathVariable Integer quizId, @PathVariable Integer questionId, @PathVariable Integer answerId) {
        courseService.deleteQuizQuestionAnswer(courseId, quizId, questionId, answerId);
        return ResponseEntity.noContent().build();
    }
}
