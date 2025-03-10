package com.oliwierkmiecik.learning_management_system.user.dto;

import com.oliwierkmiecik.learning_management_system.user.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {
    private String name;
    private String email;
    private UserRole role;

    public UserCreateDTO() {
    }

    public UserCreateDTO(String name, String email, UserRole role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
