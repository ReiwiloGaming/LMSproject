package com.oliwierkmiecik.learning_management_system.course.lesson;

import com.oliwierkmiecik.learning_management_system.course.lesson.dto.LessonCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.lesson.dto.LessonReadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
public class LessonController {
    private final LessonService lessonService;
    private final LessonMapper lessonMapper;

    public LessonController(LessonService lessonService, LessonMapper lessonMapper) {
        this.lessonService = lessonService;
        this.lessonMapper = lessonMapper;
    }

    @GetMapping("lessons")
    public ResponseEntity<List<LessonReadDTO>> getAllLessons() {
        List<Lesson> allLessons = lessonService.findAllLessons();
        List<LessonReadDTO> lessonReadDTOS = lessonMapper.LessonListToLessonReadDTOList(allLessons);

        return ResponseEntity.ok(lessonReadDTOS);
    }

    @GetMapping("lessons/{id}")
    public ResponseEntity<LessonReadDTO> getLessonById(@PathVariable Integer id) {
        Lesson lessonById = lessonService.findLessonById(id);
        LessonReadDTO lessonReadDTO = lessonMapper.lessonToLessonReadDTO(lessonById);

        return ResponseEntity.ok(lessonReadDTO);
    }

    @PostMapping("lessons")
    public ResponseEntity<LessonReadDTO> createLesson(@RequestBody LessonCreateDTO createDTO) {
        Lesson newLesson = lessonService.saveLesson(createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLesson.getId())
                .toUri();

        LessonReadDTO lessonReadDTO = lessonMapper.lessonToLessonReadDTO(newLesson);
        return ResponseEntity.created(location).body(lessonReadDTO);
    }

    @PutMapping("lessons/{id}")
    public ResponseEntity<LessonReadDTO> updateLesson(@PathVariable Integer id, @RequestBody LessonCreateDTO updates) {
        Lesson updatedLesson = lessonService.updateLesson(id, updates, true);

        LessonReadDTO lessonReadDTO = lessonMapper.lessonToLessonReadDTO(updatedLesson);
        return ResponseEntity.ok(lessonReadDTO);
    }

    @PatchMapping("lessons/{id}")
    public ResponseEntity<LessonReadDTO> partiallyUpdateLesson(@PathVariable Integer id, @RequestBody LessonCreateDTO updates) {
        Lesson updatedLesson = lessonService.updateLesson(id, updates, false);

        LessonReadDTO lessonReadDTO = lessonMapper.lessonToLessonReadDTO(updatedLesson);
        return ResponseEntity.ok(lessonReadDTO);
    }

    @DeleteMapping("lessons/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Integer id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}
