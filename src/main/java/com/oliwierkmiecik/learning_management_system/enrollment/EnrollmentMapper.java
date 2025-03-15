package com.oliwierkmiecik.learning_management_system.enrollment;

import com.oliwierkmiecik.learning_management_system.course.Course;
import com.oliwierkmiecik.learning_management_system.course.CourseRepository;
import com.oliwierkmiecik.learning_management_system.enrollment.dto.EnrollmentCreateDTO;
import com.oliwierkmiecik.learning_management_system.enrollment.dto.EnrollmentReadDTO;
import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import com.oliwierkmiecik.learning_management_system.user.User;
import com.oliwierkmiecik.learning_management_system.user.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class EnrollmentMapper {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected CourseRepository courseRepository;

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "courseId", target = "course")
    protected abstract Enrollment enrollmentCreateDTOToEnrollment(EnrollmentCreateDTO createDTO);

    @Mapping(source = "user", target = "userName")
    @Mapping(source = "course", target = "courseName")
    @Mapping(source = "enrollmentDate", target = "enrollmentDateAsString")
    protected abstract EnrollmentReadDTO enrollmentToEnrollmentReadDTO(Enrollment enrollment);

    protected List<EnrollmentReadDTO> EnrollmentListToEnrollmentReadDTOList(List<Enrollment> enrollmentList) {
        return enrollmentList.stream()
                .map(this::enrollmentToEnrollmentReadDTO)
                .toList();
    }

    protected User mapUser(Integer userId) {
        if (userId == null) return null;
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found for id: " + userId));
    }

    protected Course mapCourse(Integer courseId) {
        if (courseId == null) return null;
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found for id: " + courseId));
    }

    protected String mapUserName(User user) {
        return user.getName();
    }

    protected String mapCourseName(Course course) {
        return course.getName();
    }

    protected String mapEnrollmentDateAsString(LocalDate enrollmentDate) {
        return enrollmentDate.toString();
    }
}