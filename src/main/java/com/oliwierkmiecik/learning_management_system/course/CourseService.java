package com.oliwierkmiecik.learning_management_system.course;

import com.oliwierkmiecik.learning_management_system.course.dto.CourseCreateDTO;
import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import com.oliwierkmiecik.learning_management_system.exceptions.NullsForbiddenException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public Course findCourseById(Integer id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found for id: " + id));
    }

    public Course saveCourse(CourseCreateDTO createDTO) {
        checkIfAllFieldsPresent(createDTO);
        Course newCourse = courseMapper.courseCreateDTOToCourse(createDTO);

        return courseRepository.save(newCourse);
    }

    public Course updateCourse(Integer id, CourseCreateDTO updates, boolean ifAllFieldsRequired) {
        Course updatedCourse = findCourseById(id);
        Course updatesAsEntity = courseMapper.courseCreateDTOToCourse(updates);

        if (ifAllFieldsRequired) checkIfAllFieldsPresent(updates);

        if (updatesAsEntity.getName() != null) updatedCourse.setName(updatesAsEntity.getName());
        if (updatesAsEntity.getDescription() != null) updatedCourse.setDescription(updatesAsEntity.getDescription());

        return courseRepository.save(updatedCourse);
    }

    public void deleteCourse(Integer id) {
        courseRepository.delete(findCourseById(id));
    }


    private void checkIfAllFieldsPresent(CourseCreateDTO createDTO) {
        if (createDTO.getName() == null || createDTO.getDescription() == null) {
            throw new NullsForbiddenException("Nulls are forbidden.");
        }
    }
}
