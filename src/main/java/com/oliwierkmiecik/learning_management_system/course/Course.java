package com.oliwierkmiecik.learning_management_system.course;

import com.oliwierkmiecik.learning_management_system.enrollment.Enrollment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "course")
    private Set<Enrollment> enrollments = new HashSet<>();
}
