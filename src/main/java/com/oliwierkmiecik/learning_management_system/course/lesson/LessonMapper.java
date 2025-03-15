package com.oliwierkmiecik.learning_management_system.course.lesson;

import com.oliwierkmiecik.learning_management_system.course.Course;
import com.oliwierkmiecik.learning_management_system.course.CourseRepository;
import com.oliwierkmiecik.learning_management_system.course.lesson.dto.LessonCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.lesson.dto.LessonReadDTO;
import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LessonMapper {
    @Autowired
    protected CourseRepository courseRepository;

    @Mapping(source = "courseId", target = "course")
    protected abstract Lesson lessonCreateDTOToLesson(LessonCreateDTO lessonCreateDTO);

    public abstract LessonReadDTO lessonToLessonReadDTO(Lesson lesson);

    public List<LessonReadDTO> lessonListToLessonReadDTOList(List<Lesson> lessonList) {
        return lessonList.stream()
                .map(this::lessonToLessonReadDTO)
                .toList();
    }

    protected Course mapCourse(Integer courseId) {
        if (courseId== null) return null;
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found for id: " + courseId));
    }
}
