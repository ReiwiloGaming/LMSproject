package com.oliwierkmiecik.learning_management_system.user;

import com.oliwierkmiecik.learning_management_system.exceptions.NotFoundException;
import com.oliwierkmiecik.learning_management_system.exceptions.NullsForbiddenException;
import com.oliwierkmiecik.learning_management_system.user.dto.UserCreateDTO;
import com.oliwierkmiecik.learning_management_system.user.dto.UserReadDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found."));
    }

    public User saveUser(UserCreateDTO userCreateDTO) {
        checkIfAllFieldsPresent(userCreateDTO);
        User newUser = userMapper.userCreateDTOToUser(userCreateDTO);
        return userRepository.save(newUser);
    }

    public User updateUser(Integer id, UserCreateDTO updates, boolean ifAllFieldsRequired) {
        User updatedUser = findUserById(id);

        if (ifAllFieldsRequired) checkIfAllFieldsPresent(updates);

        if (updates.getName() != null) updatedUser.setName(updates.getName());
        if (updates.getEmail() != null) updatedUser.setEmail(updates.getEmail());
        if (updates.getRole() != null) updatedUser.setRole(updates.getRole());

        return userRepository.save(updatedUser);
    }

    public void deleteUser(Integer id) {
        userRepository.delete(findUserById(id));
    }


    private void checkIfAllFieldsPresent(UserCreateDTO userCreateDTO) {
        if (userCreateDTO.getName() == null || userCreateDTO.getEmail() == null || userCreateDTO.getRole() == null) {
            throw new NullsForbiddenException("Nulls are forbidden");
        }
    }
}
