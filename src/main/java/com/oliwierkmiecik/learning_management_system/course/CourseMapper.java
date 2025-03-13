package com.oliwierkmiecik.learning_management_system.course;

import com.oliwierkmiecik.learning_management_system.course.dto.CourseCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.dto.CourseReadDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CourseMapper {
    protected abstract Course courseCreateDTOToCourse(CourseCreateDTO createDTO);

    protected abstract CourseReadDTO courseToCourseReadDTO(Course course);

    protected List<CourseReadDTO> courseListToCourseReadDTOList(List<Course> courseList) {
        return courseList.stream()
                .map(this::courseToCourseReadDTO)
                .toList();
    }
}
