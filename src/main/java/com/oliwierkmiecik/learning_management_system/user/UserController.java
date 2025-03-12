package com.oliwierkmiecik.learning_management_system.user;

import com.oliwierkmiecik.learning_management_system.user.dto.UserCreateDTO;
import com.oliwierkmiecik.learning_management_system.user.dto.UserReadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("users")
    public ResponseEntity<List<UserReadDTO>> getAllUsers() {
        return ResponseEntity.ok(userMapper.entityListToReadDTOList(userService.findAllUsers()));
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserReadDTO> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userMapper.userToUserReadDTO(userService.findUserById(id)));
    }

    @PostMapping("users")
    public ResponseEntity<UserReadDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        User newUser = userService.saveUser(userCreateDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();

        UserReadDTO userReadDTO = userMapper.userToUserReadDTO(newUser);

        return ResponseEntity.created(location).body(userReadDTO);
    }

    @PutMapping("users/{id}")
    public ResponseEntity<UserReadDTO> updateUser(@PathVariable Integer id, @RequestBody UserCreateDTO updates) {
        return ResponseEntity.ok(userMapper.userToUserReadDTO(userService.updateUser(id, updates, true)));
    }

    @PatchMapping("users/{id}")
    public ResponseEntity<UserReadDTO> partiallyUpdateUser(@PathVariable Integer id, @RequestBody UserCreateDTO updates) {
        return ResponseEntity.ok(userMapper.userToUserReadDTO(userService.updateUser(id, updates, false)));
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
