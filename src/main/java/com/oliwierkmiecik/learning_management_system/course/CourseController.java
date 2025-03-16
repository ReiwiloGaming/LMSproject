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

    public CourseController(CourseService courseService, CourseMapper courseMapper, LessonMapper lessonMapper, QuizMapper quizMapper, QuizQuestionMapper quizQuestionMapper, QuizQuestionAnswerMapper quizQuestionAnswerMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
        this.lessonMapper = lessonMapper;
        this.quizMapper = quizMapper;
        this.quizQuestionMapper = quizQuestionMapper;
        this.quizQuestionAnswerMapper = quizQuestionAnswerMapper;
    }

    //-----------------------------------------------------------------------------------------------------------------
    //                                          CONTROLLING COURSES

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
    //                                          CONTROLLING LESSONS

    @GetMapping("courses/lessons")
    public ResponseEntity<List<LessonReadDTO>> getAllLessons() {
        List<Lesson> allLessons = courseService.findAllLessons();
        List<LessonReadDTO> lessonReadDTOS = lessonMapper.lessonListToLessonReadDTOList(allLessons);

        return ResponseEntity.ok(lessonReadDTOS);
    }

    @GetMapping("courses/lessons{id}")
    public ResponseEntity<LessonReadDTO> getLessonById(@PathVariable Integer id) {
        Lesson lessonById = courseService.findLessonById(id);
        LessonReadDTO lessonReadDTO = lessonMapper.lessonToLessonReadDTO(lessonById);

        return ResponseEntity.ok(lessonReadDTO);
    }

    @PostMapping("courses/lessons")
    public ResponseEntity<LessonReadDTO> createLesson(@RequestBody LessonCreateDTO createDTO) {
        Lesson newLesson = courseService.saveLesson(createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLesson.getId())
                .toUri();

        LessonReadDTO lessonReadDTO = lessonMapper.lessonToLessonReadDTO(newLesson);
        return ResponseEntity.created(location).body(lessonReadDTO);
    }

    @PutMapping("courses/lessons/{id}")
    public ResponseEntity<LessonReadDTO> updateLesson(@PathVariable Integer id, @RequestBody LessonCreateDTO updates) {
        Lesson updatedLesson = courseService.updateLesson(id, updates, true);

        LessonReadDTO lessonReadDTO = lessonMapper.lessonToLessonReadDTO(updatedLesson);
        return ResponseEntity.ok(lessonReadDTO);
    }

    @PatchMapping("courses/lessons/{id}")
    public ResponseEntity<LessonReadDTO> partiallyUpdateLesson(@PathVariable Integer id, @RequestBody LessonCreateDTO updates) {
        Lesson updatedLesson = courseService.updateLesson(id, updates, false);

        LessonReadDTO lessonReadDTO = lessonMapper.lessonToLessonReadDTO(updatedLesson);
        return ResponseEntity.ok(lessonReadDTO);
    }

    @DeleteMapping("courses/lessons/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Integer id) {
        courseService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }

    //-----------------------------------------------------------------------------------------------------------------
    //                                            CONTROLLING QUIZES

    @GetMapping("courses/quizes")
    public ResponseEntity<List<QuizReadDTO>> getAllQuizes() {
        List<Quiz> allQuizes = courseService.findAllQuizes();
        List<QuizReadDTO> quizReadDTOS = quizMapper.quizListToQuizReadDTOList(allQuizes);

        return ResponseEntity.ok(quizReadDTOS);
    }

    @GetMapping("courses/quizes/{id}")
    public ResponseEntity<QuizReadDTO> getQuizById(@PathVariable Integer id) {
        Quiz quizById = courseService.findQuizById(id);
        QuizReadDTO quizReadDTO = quizMapper.quizToQuizReadDTO(quizById);

        return ResponseEntity.ok(quizReadDTO);
    }

    @PostMapping("courses/quizes")
    public ResponseEntity<QuizReadDTO> createQuiz(@RequestBody QuizCreateDTO createDTO) {
        Quiz newQuiz = courseService.saveQuiz(createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newQuiz.getId())
                .toUri();

        QuizReadDTO quizReadDTO = quizMapper.quizToQuizReadDTO(newQuiz);

        return ResponseEntity.created(location).body(quizReadDTO);
    }

    @PutMapping("courses/quizes/{id}")
    public ResponseEntity<QuizReadDTO> updateQuiz(@PathVariable Integer id, @RequestBody QuizCreateDTO updates) {
        Quiz updatedQuiz = courseService.updateQuiz(id, updates, true);
        QuizReadDTO quizReadDTO = quizMapper.quizToQuizReadDTO(updatedQuiz);

        return ResponseEntity.ok(quizReadDTO);
    }

    @PatchMapping("courses/quizes/{id}")
    public ResponseEntity<QuizReadDTO> partiallyUpdateQuiz(@PathVariable Integer id, @RequestBody QuizCreateDTO updates) {
        Quiz updatedQuiz = courseService.updateQuiz(id, updates, false);
        QuizReadDTO quizReadDTO = quizMapper.quizToQuizReadDTO(updatedQuiz);

        return ResponseEntity.ok(quizReadDTO);
    }

    @DeleteMapping("courses/quizes/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Integer id) {
        courseService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

    //-----------------------------------------------------------------------------------------------------------------
    //                                        CONTROLLING QUIZ QUESTIONS

    @GetMapping("courses/quizes/questions")
    public ResponseEntity<List<QuizQuestionReadDTO>> getAllQuestions() {
        List<QuizQuestion> quizQuestions = courseService.findAllQuizQuestions();
        List<QuizQuestionReadDTO> quizQuestionReadDTOS = quizQuestionMapper.EntityListToReadDTOList(quizQuestions);
        return ResponseEntity.ok(quizQuestionReadDTOS);
    }

    @GetMapping("courses/quizes/questions/{id}")
    public ResponseEntity<QuizQuestionReadDTO> getQuestionById(@PathVariable Integer id) {
        QuizQuestion questionById = courseService.findQuizQuestionById(id);
        QuizQuestionReadDTO quizQuestionReadDTO = quizQuestionMapper.QuizQuestionToQuizQuestionReadDTO(questionById);
        return ResponseEntity.ok(quizQuestionReadDTO);
    }

    @PostMapping("courses/quizes/questions")
    public ResponseEntity<QuizQuestionReadDTO> createQuestion(@RequestBody QuizQuestionCreateDTO createDTO) {
        QuizQuestion newQuestion = courseService.saveQuizQuestion(createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newQuestion.getId())
                .toUri();

        QuizQuestionReadDTO newQuestionReadDTO = quizQuestionMapper.QuizQuestionToQuizQuestionReadDTO(newQuestion);

        return ResponseEntity.created(location).body(newQuestionReadDTO);
    }

    @PutMapping("courses/quizes/questions/{id}")
    public ResponseEntity<QuizQuestionReadDTO> updateQuestion(@PathVariable Integer id, @RequestBody QuizQuestionCreateDTO updates) {
        QuizQuestion updatedQuestion = courseService.updateQuizQuestion(id, updates, true);
        QuizQuestionReadDTO updatedQuestionReadDTO = quizQuestionMapper.QuizQuestionToQuizQuestionReadDTO(updatedQuestion);

        return ResponseEntity.ok(updatedQuestionReadDTO);
    }

    @PatchMapping("courses/quizes/questions/{id}")
    public ResponseEntity<QuizQuestionReadDTO> partiallyUpdateQuestion(@PathVariable Integer id, @RequestBody QuizQuestionCreateDTO updates) {
        QuizQuestion updatedQuestion = courseService.updateQuizQuestion(id, updates, false);
        QuizQuestionReadDTO updatedQuestionReadDTO = quizQuestionMapper.QuizQuestionToQuizQuestionReadDTO(updatedQuestion);

        return ResponseEntity.ok(updatedQuestionReadDTO);
    }

    @DeleteMapping("courses/quizes/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Integer id) {
        courseService.deleteQuizQuestion(id);
        return ResponseEntity.noContent().build();
    }
    //-----------------------------------------------------------------------------------------------------------------
    //                                      CONTROLLING QUIZ QUESTION ANSWERS

    @GetMapping("quizquestionanswers")
    public ResponseEntity<List<QuizQuestionAnswerReadDTO>> getAllAnswers() {
        return ResponseEntity.ok(quizQuestionAnswerMapper.entityListToReadDTOList(courseService.findAllQuizQuestionAnswers()));
    }

    @GetMapping("quizquestionanswers/{id}")
    public ResponseEntity<QuizQuestionAnswerReadDTO> getAnswerById(@PathVariable Integer id) {
        return ResponseEntity.ok(quizQuestionAnswerMapper.QuizQuestionAnswerToQuizQuestionAnswerReadDTO(courseService.findQuizQuestionAnswerById(id)));
    }

    @PostMapping("quizquestionanswers")
    public ResponseEntity<QuizQuestionAnswerReadDTO> createAnswer(@RequestBody QuizQuestionAnswerCreateDTO createDTO) {
        QuizQuestionAnswer newAnswer = courseService.saveQuizQuestionAnswer(createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAnswer.getId())
                .toUri();

        QuizQuestionAnswerReadDTO answerReadDTO = quizQuestionAnswerMapper.QuizQuestionAnswerToQuizQuestionAnswerReadDTO(newAnswer);

        return ResponseEntity.created(location).body(answerReadDTO);
    }

    @PutMapping("quizquestionanswers/{id}")
    public ResponseEntity<QuizQuestionAnswerReadDTO> updateAnswer(@PathVariable Integer id, @RequestBody QuizQuestionAnswerCreateDTO updates) {
        QuizQuestionAnswer updatedAnswer = courseService.updateQuizQuestionAnswer(id, updates, true);
        QuizQuestionAnswerReadDTO answerReadDTO = quizQuestionAnswerMapper.QuizQuestionAnswerToQuizQuestionAnswerReadDTO(updatedAnswer);
        return ResponseEntity.ok(answerReadDTO);
    }

    @PatchMapping("quizquestionanswers/{id}")
    public ResponseEntity<QuizQuestionAnswerReadDTO> partiallyUpdateAnswer(@PathVariable Integer id, @RequestBody QuizQuestionAnswerCreateDTO updates) {
        QuizQuestionAnswer updatedAnswer = courseService.updateQuizQuestionAnswer(id, updates, false);
        QuizQuestionAnswerReadDTO answerReadDTO = quizQuestionAnswerMapper.QuizQuestionAnswerToQuizQuestionAnswerReadDTO(updatedAnswer);
        return ResponseEntity.ok(answerReadDTO);
    }

    @DeleteMapping("quizquestionanswers/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Integer id) {
        courseService.deleteQuizQuestionAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
