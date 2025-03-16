package com.oliwierkmiecik.learning_management_system.user;

import com.oliwierkmiecik.learning_management_system.user.dto.UserCreateDTO;
import com.oliwierkmiecik.learning_management_system.user.dto.UserReadDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @Operation(description = "Downloads all users",
    responses = {
            @ApiResponse(responseCode = "200", description = "Users downloaded successfully")
    })
    @GetMapping("users")
    public ResponseEntity<List<UserReadDTO>> getAllUsers() {
        return ResponseEntity.ok(userMapper.entityListToReadDTOList(userService.findAllUsers()));
    }

    @Operation(description = "Downloads user by its id",
    responses = {
            @ApiResponse(responseCode = "200", description = "User downloaded successfully"),
            @ApiResponse(responseCode = "404", description = "User for this id not found"),
            @ApiResponse(responseCode = "400", description = "Most likely stated id was not a number")
    }, parameters = {
            @Parameter(name = "id", description = "Id for the user that is meant to be downloaded.")
    })
    @GetMapping("users/{id}")
    public ResponseEntity<UserReadDTO> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userMapper.userToUserReadDTO(userService.findUserById(id)));
    }

    @Operation(description = "Creates a new user in the database",
    responses = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "403", description = "Not all fields were filled")
    })
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

    @Operation(description = "Updates the whole user entity in the database")
    @PutMapping("users/{id}")
    public ResponseEntity<UserReadDTO> updateUser(@PathVariable Integer id, @RequestBody UserCreateDTO updates) {
        return ResponseEntity.ok(userMapper.userToUserReadDTO(userService.updateUser(id, updates, true)));
    }

    @Operation(description = "Partially updates user entity in the database")
    @PatchMapping("users/{id}")
    public ResponseEntity<UserReadDTO> partiallyUpdateUser(@PathVariable Integer id, @RequestBody UserCreateDTO updates) {
        return ResponseEntity.ok(userMapper.userToUserReadDTO(userService.updateUser(id, updates, false)));
    }

    @Operation(description = "Deletes user from database")
    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
