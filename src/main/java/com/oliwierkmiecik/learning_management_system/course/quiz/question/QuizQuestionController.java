package com.oliwierkmiecik.learning_management_system.course.quiz.question;

import com.oliwierkmiecik.learning_management_system.course.quiz.question.dto.QuizQuestionCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.dto.QuizQuestionReadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class QuizQuestionController {
    private final QuizQuestionService quizQuestionService;
    private final QuizQuestionMapper quizQuestionMapper;

    public QuizQuestionController(QuizQuestionService quizQuestionService, QuizQuestionMapper quizQuestionMapper) {
        this.quizQuestionService = quizQuestionService;
        this.quizQuestionMapper = quizQuestionMapper;
    }

    @GetMapping("quizquestions")
    public ResponseEntity<List<QuizQuestionReadDTO>> getAllQuestions() {
        List<QuizQuestion> quizQuestions = quizQuestionService.findAllQuestions();
        List<QuizQuestionReadDTO> quizQuestionReadDTOS = quizQuestionMapper.EntityListToReadDTOList(quizQuestions);
        return ResponseEntity.ok(quizQuestionReadDTOS);
    }

    @GetMapping("quizquestions/{id}")
    public ResponseEntity<QuizQuestionReadDTO> getQuestionById(@PathVariable Integer id) {
        QuizQuestion questionById = quizQuestionService.findQuestionById(id);
        QuizQuestionReadDTO quizQuestionReadDTO = quizQuestionMapper.QuizQuestionToQuizQuestionReadDTO(questionById);
        return ResponseEntity.ok(quizQuestionReadDTO);
    }

    @PostMapping("quizquestions")
    public ResponseEntity<QuizQuestionReadDTO> createQuestion(@RequestBody QuizQuestionCreateDTO createDTO) {
        QuizQuestion newQuestion = quizQuestionService.saveQuestion(createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newQuestion.getId())
                .toUri();

        QuizQuestionReadDTO newQuestionReadDTO = quizQuestionMapper.QuizQuestionToQuizQuestionReadDTO(newQuestion);

        return ResponseEntity.created(location).body(newQuestionReadDTO);
    }

    @PutMapping("quizquestions/{id}")
    public ResponseEntity<QuizQuestionReadDTO> updateQuestion(@PathVariable Integer id, @RequestBody QuizQuestionCreateDTO updates) {
        QuizQuestion updatedQuestion = quizQuestionService.updateQuestion(id, updates, true);
        QuizQuestionReadDTO updatedQuestionReadDTO = quizQuestionMapper.QuizQuestionToQuizQuestionReadDTO(updatedQuestion);

        return ResponseEntity.ok(updatedQuestionReadDTO);
    }

    @PatchMapping("quizquestions/{id}")
    public ResponseEntity<QuizQuestionReadDTO> partiallyUpdateQuestion(@PathVariable Integer id, @RequestBody QuizQuestionCreateDTO updates) {
        QuizQuestion updatedQuestion = quizQuestionService.updateQuestion(id, updates, false);
        QuizQuestionReadDTO updatedQuestionReadDTO = quizQuestionMapper.QuizQuestionToQuizQuestionReadDTO(updatedQuestion);

        return ResponseEntity.ok(updatedQuestionReadDTO);
    }

    @DeleteMapping("quizquestions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Integer id) {
        quizQuestionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
