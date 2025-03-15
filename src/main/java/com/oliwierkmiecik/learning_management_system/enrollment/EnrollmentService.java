package com.oliwierkmiecik.learning_management_system.enrollment;

import com.oliwierkmiecik.learning_management_system.enrollment.dto.EnrollmentCreateDTO;
import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import com.oliwierkmiecik.learning_management_system.exceptions.NullsForbiddenException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, EnrollmentMapper enrollmentMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentMapper = enrollmentMapper;
    }

    public List<Enrollment> findAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment findEnrollmentById(Integer id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Enrollment not found for id: " + id));
    }

    public Enrollment saveEnrollment(EnrollmentCreateDTO createDTO) {
        checkIfAllFieldsPresent(createDTO);
        Enrollment newEnrollment = enrollmentMapper.enrollmentCreateDTOToEnrollment(createDTO);
        return enrollmentRepository.save(newEnrollment);
    }

    public Enrollment updateEnrollment(Integer id, EnrollmentCreateDTO updates, boolean ifAllFieldsRequired) {
        if (ifAllFieldsRequired) checkIfAllFieldsPresent(updates);
        Enrollment updatedEnrollment = findEnrollmentById(id);

        Enrollment updatesAsEntity = enrollmentMapper.enrollmentCreateDTOToEnrollment(updates);

        if (updatesAsEntity.getCourse() != null) updatedEnrollment.setCourse(updatesAsEntity.getCourse());
        if (updatesAsEntity.getUser() != null) updatedEnrollment.setUser(updatesAsEntity.getUser());

        return updatedEnrollment;
    }

    public void deleteEnrollment(Integer id) {
        enrollmentRepository.delete(findEnrollmentById(id));
    }


    private void checkIfAllFieldsPresent(EnrollmentCreateDTO createDTO) {
        if (createDTO.getUserId() == null || createDTO.getCourseId() == null) {
            throw new NullsForbiddenException("Nulls are forbidden");
        }
    }
}
