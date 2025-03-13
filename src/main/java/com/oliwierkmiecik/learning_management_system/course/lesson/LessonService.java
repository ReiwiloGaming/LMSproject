package com.oliwierkmiecik.learning_management_system.course.lesson;

import com.oliwierkmiecik.learning_management_system.course.lesson.dto.LessonCreateDTO;
import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import com.oliwierkmiecik.learning_management_system.exceptions.NullsForbiddenException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
    }

    public List<Lesson> findAllLessons() {
        return lessonRepository.findAll();
    }

    public Lesson findLessonById(Integer id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lesson not found for id: " + id));
    }

    public Lesson saveLesson(LessonCreateDTO createDTO) {
        Lesson newLesson = lessonMapper.lessonCreateDTOToLesson(createDTO);
        return lessonRepository.save(newLesson);
    }

    public Lesson updateLesson(Integer id, LessonCreateDTO updates, boolean ifAllFieldsRequired) {
        Lesson updatedLesson = findLessonById(id);
        Lesson updatesAsEntity = lessonMapper.lessonCreateDTOToLesson(updates);

        if (ifAllFieldsRequired) checkIfAllFieldsPresent(updates);

        if (updatesAsEntity.getName() != null) updatedLesson.setName(updatesAsEntity.getName());
        if (updatesAsEntity.getContent() != null) updatedLesson.setContent(updatesAsEntity.getContent());
        if (updatesAsEntity.getCourse() != null) updatedLesson.setCourse(updatesAsEntity.getCourse());
        if (updatesAsEntity.getInCoursePosition() != null) updatedLesson.setInCoursePosition(updatesAsEntity.getInCoursePosition());

        return lessonRepository.save(updatedLesson);
    }

    public void deleteLesson(Integer id) {
        lessonRepository.delete(findLessonById(id));
    }


    private void checkIfAllFieldsPresent(LessonCreateDTO createDTO) {
        if (createDTO.getContent() == null || createDTO.getName() == null || createDTO.getCourseId() == null || createDTO.getInCoursePosition() == null) {
            throw new NullsForbiddenException("Nulls are forbidden.");
        }
    }
}
