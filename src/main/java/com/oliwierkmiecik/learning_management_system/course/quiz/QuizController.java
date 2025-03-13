package com.oliwierkmiecik.learning_management_system.course.quiz;

import com.oliwierkmiecik.learning_management_system.course.quiz.dto.QuizCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.dto.QuizReadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
public class QuizController {
    private final QuizService quizService;
    private final QuizMapper quizMapper;

    public QuizController(QuizService quizService, QuizMapper quizMapper) {
        this.quizService = quizService;
        this.quizMapper = quizMapper;
    }

    @GetMapping("quizes")
    public ResponseEntity<List<QuizReadDTO>> getAllQuizes() {
        List<Quiz> allQuizes = quizService.findAllQuizes();
        List<QuizReadDTO> quizReadDTOS = quizMapper.QuizListToQuizReadDTOList(allQuizes);

        return ResponseEntity.ok(quizReadDTOS);
    }

    @GetMapping("quizes/{id}")
    public ResponseEntity<QuizReadDTO> getQuizById(@PathVariable Integer id) {
        Quiz quizById = quizService.findQuizById(id);
        QuizReadDTO quizReadDTO = quizMapper.quizToQuizReadDTO(quizById);

        return ResponseEntity.ok(quizReadDTO);
    }

    @PostMapping("quizes")
    public ResponseEntity<QuizReadDTO> createQuiz(@RequestBody QuizCreateDTO createDTO) {
        Quiz newQuiz = quizService.saveQuiz(createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newQuiz.getId())
                .toUri();

        QuizReadDTO quizReadDTO = quizMapper.quizToQuizReadDTO(newQuiz);

        return ResponseEntity.created(location).body(quizReadDTO);
    }

    @PutMapping("quizes/{id}")
    public ResponseEntity<QuizReadDTO> updateQuiz(@PathVariable Integer id, @RequestBody QuizCreateDTO updates) {
        Quiz updatedQuiz = quizService.updateQuiz(id, updates, true);
        QuizReadDTO quizReadDTO = quizMapper.quizToQuizReadDTO(updatedQuiz);

        return ResponseEntity.ok(quizReadDTO);
    }

    @PatchMapping("quizes/{id}")
    public ResponseEntity<QuizReadDTO> partiallyUpdateQuiz(@PathVariable Integer id, @RequestBody QuizCreateDTO updates) {
        Quiz updatedQuiz = quizService.updateQuiz(id, updates, false);
        QuizReadDTO quizReadDTO = quizMapper.quizToQuizReadDTO(updatedQuiz);

        return ResponseEntity.ok(quizReadDTO);
    }

    @DeleteMapping("quizes/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Integer id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }
}
