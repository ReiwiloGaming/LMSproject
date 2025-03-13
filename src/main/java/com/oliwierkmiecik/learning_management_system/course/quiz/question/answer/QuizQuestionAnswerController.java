package com.oliwierkmiecik.learning_management_system.course.quiz.question.answer;

import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto.QuizQuestionAnswerCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.quiz.question.answer.dto.QuizQuestionAnswerReadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
public class QuizQuestionAnswerController {
    private final QuizQuestionAnswerService answerService;
    private final QuizQuestionAnswerMapper answerMapper;

    public QuizQuestionAnswerController(QuizQuestionAnswerService answerService, QuizQuestionAnswerMapper answerMapper) {
        this.answerService = answerService;
        this.answerMapper = answerMapper;
    }

    @GetMapping("quizquestionanswers")
    public ResponseEntity<List<QuizQuestionAnswerReadDTO>> getAllAnswers() {
        return ResponseEntity.ok(answerMapper.entityListToReadDTOList(answerService.findAllAnswers()));
    }

    @GetMapping("quizquestionanswers/{id}")
    public ResponseEntity<QuizQuestionAnswerReadDTO> getAnswerById(@PathVariable Integer id) {
        return ResponseEntity.ok(answerMapper.QuizQuestionAnswerToQuizQuestionAnswerReadDTO(answerService.findAnswerById(id)));
    }

    @PostMapping("quizquestionanswers")
    public ResponseEntity<QuizQuestionAnswerReadDTO> createAnswer(@RequestBody QuizQuestionAnswerCreateDTO createDTO) {
        QuizQuestionAnswer newAnswer = answerService.saveAnswer(createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAnswer.getId())
                .toUri();

        QuizQuestionAnswerReadDTO answerReadDTO = answerMapper.QuizQuestionAnswerToQuizQuestionAnswerReadDTO(newAnswer);

        return ResponseEntity.created(location).body(answerReadDTO);
    }

    @PutMapping("quizquestionanswers/{id}")
    public ResponseEntity<QuizQuestionAnswerReadDTO> updateAnswer(@PathVariable Integer id, @RequestBody QuizQuestionAnswerCreateDTO updates) {
        QuizQuestionAnswer updatedAnswer = answerService.updateAnswer(id, updates, true);
        QuizQuestionAnswerReadDTO answerReadDTO = answerMapper.QuizQuestionAnswerToQuizQuestionAnswerReadDTO(updatedAnswer);
        return ResponseEntity.ok(answerReadDTO);
    }

    @PatchMapping("quizquestionanswers/{id}")
    public ResponseEntity<QuizQuestionAnswerReadDTO> partiallyUpdateAnswer(@PathVariable Integer id, @RequestBody QuizQuestionAnswerCreateDTO updates) {
        QuizQuestionAnswer updatedAnswer = answerService.updateAnswer(id, updates, false);
        QuizQuestionAnswerReadDTO answerReadDTO = answerMapper.QuizQuestionAnswerToQuizQuestionAnswerReadDTO(updatedAnswer);
        return ResponseEntity.ok(answerReadDTO);
    }

    @DeleteMapping("quizquestionanswers/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Integer id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
