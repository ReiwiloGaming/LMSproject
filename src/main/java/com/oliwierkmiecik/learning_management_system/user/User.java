package com.oliwierkmiecik.learning_management_system.user;

import com.oliwierkmiecik.learning_management_system.enrollment.Enrollment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column (length = 20, nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private Set<Enrollment> enrollments = new HashSet<>();

    public User() {
    }

    public User(String name, String email, UserRole role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
