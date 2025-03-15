package com.oliwierkmiecik.learning_management_system.enrollment;

import com.oliwierkmiecik.learning_management_system.enrollment.dto.EnrollmentCreateDTO;
import com.oliwierkmiecik.learning_management_system.enrollment.dto.EnrollmentReadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentController(EnrollmentService enrollmentService, EnrollmentMapper enrollmentMapper) {
        this.enrollmentService = enrollmentService;
        this.enrollmentMapper = enrollmentMapper;
    }

    @GetMapping("enrollments")
    public ResponseEntity<List<EnrollmentReadDTO>> getAllEnrollments() {
        List<Enrollment> allEnrollments = enrollmentService.findAllEnrollments();
        List<EnrollmentReadDTO> enrollmentReadDTOS = enrollmentMapper.EnrollmentListToEnrollmentReadDTOList(allEnrollments);

        return ResponseEntity.ok(enrollmentReadDTOS);
    }

    @GetMapping("enrollments/{id}")
    public ResponseEntity<EnrollmentReadDTO> getEnrollmentById(@PathVariable Integer id) {
        Enrollment enrollmentById = enrollmentService.findEnrollmentById(id);
        EnrollmentReadDTO enrollmentReadDTO = enrollmentMapper.enrollmentToEnrollmentReadDTO(enrollmentById);

        return ResponseEntity.ok(enrollmentReadDTO);
    }

    @PostMapping("enrollments")
    public ResponseEntity<EnrollmentReadDTO> createEnrollment(@RequestBody EnrollmentCreateDTO createDTO) {
        Enrollment newEnrollment = enrollmentService.saveEnrollment(createDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEnrollment.getId())
                .toUri();

        EnrollmentReadDTO enrollmentReadDTO = enrollmentMapper.enrollmentToEnrollmentReadDTO(newEnrollment);

        return ResponseEntity.created(location).body(enrollmentReadDTO);
    }

    @PutMapping("enrollments/{id}")
    public ResponseEntity<EnrollmentReadDTO> updateEnrollment(@PathVariable Integer id, @RequestBody EnrollmentCreateDTO updates) {
        Enrollment updatedEnrollment = enrollmentService.updateEnrollment(id, updates, true);
        EnrollmentReadDTO enrollmentReadDTO = enrollmentMapper.enrollmentToEnrollmentReadDTO(updatedEnrollment);

        return ResponseEntity.ok(enrollmentReadDTO);
    }

    @PatchMapping("enrollments/{id}")
    public ResponseEntity<EnrollmentReadDTO> partiallyUpdateEnrollment(@PathVariable Integer id, @RequestBody EnrollmentCreateDTO updates) {
        Enrollment updatedEnrollment = enrollmentService.updateEnrollment(id, updates, false);
        EnrollmentReadDTO enrollmentReadDTO = enrollmentMapper.enrollmentToEnrollmentReadDTO(updatedEnrollment);

        return ResponseEntity.ok(enrollmentReadDTO);
    }

    @DeleteMapping("enrollments/{id}")
    public ResponseEntity<Void> deleteEnrollments(@PathVariable Integer id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}
