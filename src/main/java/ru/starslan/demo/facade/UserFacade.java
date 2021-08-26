package ru.starslan.demo.facade;

import org.springframework.stereotype.Component;
import ru.starslan.demo.dto.UserDTO;
import ru.starslan.demo.entity.User;

@Component
public class UserFacade {

    public UserDTO userToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getName());
        userDTO.setLastname(user.getLastName());
        userDTO.setUsername(user.getUsername());
        userDTO.setBio(user.getBio());
        return  userDTO;

    }

}
