package com.oliwierkmiecik.learning_management_system.user.dto;

import com.oliwierkmiecik.learning_management_system.user.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReadDTO {
    private Integer id;
    private String username;
    private String email;
    private UserRole role;

    public UserReadDTO() {
    }

    public UserReadDTO(Integer id, String username, String email, UserRole role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
