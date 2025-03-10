package com.oliwierkmiecik.learning_management_system.user;

import com.oliwierkmiecik.learning_management_system.user.dto.UserCreateDTO;
import com.oliwierkmiecik.learning_management_system.user.dto.UserReadDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "name", target = "username") //Ta adnotacja zamieni pole o nazwie name z encji User na pole o nazwie username w DTO
    UserReadDTO userToUserReadDTO(User user);

    User userCreateDTOToUser(UserCreateDTO userCreateDTO);
}
