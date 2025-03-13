package com.oliwierkmiecik.learning_management_system.course;

import com.oliwierkmiecik.learning_management_system.course.dto.CourseCreateDTO;
import com.oliwierkmiecik.learning_management_system.course.dto.CourseReadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;

    public CourseController(CourseService courseService, CourseMapper courseMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    @GetMapping("courses")
    public ResponseEntity<List<CourseReadDTO>> getAllCourses() {
        List<Course> allCourses = courseService.findAllCourses();
        List<CourseReadDTO> courseReadDTOS = courseMapper.courseListToCourseReadDTOList(allCourses);

        return ResponseEntity.ok(courseReadDTOS);
    }

    @GetMapping("courses/{id}")
    public ResponseEntity<CourseReadDTO> getCourseById(@PathVariable Integer id) {
        Course courseById = courseService.findCourseById(id);
        CourseReadDTO courseReadDTO = courseMapper.courseToCourseReadDTO(courseById);

        return ResponseEntity.ok(courseReadDTO);
    }

    @PostMapping("courses")
    public ResponseEntity<CourseReadDTO> createCourse(@RequestBody CourseCreateDTO createDTO) {
        Course newCourse = courseService.saveCourse(createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{/{id}")
                .buildAndExpand(newCourse.getId())
                .toUri();

        CourseReadDTO courseReadDTO = courseMapper.courseToCourseReadDTO(newCourse);

        return ResponseEntity.created(location).body(courseReadDTO);
    }

    @PutMapping("courses/{id}")
    public ResponseEntity<CourseReadDTO> updateCourse(@PathVariable Integer id, @RequestBody CourseCreateDTO updates) {
        Course updatedCourse = courseService.updateCourse(id, updates, true);

        CourseReadDTO courseReadDTO = courseMapper.courseToCourseReadDTO(updatedCourse);

        return ResponseEntity.ok(courseReadDTO);
    }

    @PatchMapping("courses/{id}")
    public ResponseEntity<CourseReadDTO> partiallyUpdateCourse(@PathVariable Integer id, @RequestBody CourseCreateDTO updates) {
        Course updatedCourse = courseService.updateCourse(id, updates, false);

        CourseReadDTO courseReadDTO = courseMapper.courseToCourseReadDTO(updatedCourse);

        return ResponseEntity.ok(courseReadDTO);
    }

    @DeleteMapping("courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
