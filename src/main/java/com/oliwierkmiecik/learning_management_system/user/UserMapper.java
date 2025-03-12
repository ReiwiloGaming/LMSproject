package com.oliwierkmiecik.learning_management_system.user;

import com.oliwierkmiecik.learning_management_system.user.dto.UserCreateDTO;
import com.oliwierkmiecik.learning_management_system.user.dto.UserReadDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mapping(source = "name", target = "username") //Ta adnotacja zamieni pole o nazwie name z encji User na pole o nazwie username w DTO
    protected abstract UserReadDTO userToUserReadDTO(User user);

    protected abstract User userCreateDTOToUser(UserCreateDTO userCreateDTO);

    protected List<UserReadDTO> entityListToReadDTOList(List<User> userList) {
        return userList.stream().map(this::userToUserReadDTO).toList();
    }
}
