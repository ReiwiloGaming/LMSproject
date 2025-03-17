package com.oliwierkmiecik.learning_management_system.course.lesson;

import com.oliwierkmiecik.learning_management_system.course.lesson.dto.LessonCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.lesson.dto.LessonReadDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LessonMapper {
    public abstract Lesson lessonCreateDTOToLesson(LessonCreateDTO lessonCreateDTO);

    public abstract LessonReadDTO lessonToLessonReadDTO(Lesson lesson);

    public List<LessonReadDTO> lessonListToLessonReadDTOList(List<Lesson> lessonList) {
        return lessonList.stream()
                .map(this::lessonToLessonReadDTO)
                .toList();
    }
}
