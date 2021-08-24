package ru.starslan.demo.service;


import jdk.jshell.spi.ExecutionControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.starslan.demo.dto.UserDTO;
import ru.starslan.demo.entity.User;
import ru.starslan.demo.entity.enums.ERole;
import ru.starslan.demo.exceptions.UserExistException;
import ru.starslan.demo.payload.request.SignRequest;
import ru.starslan.demo.repository.UserRepository;

import java.security.Principal;

@Service
public class UserService {

    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private static UserRepository userRepository;
    private static BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignRequest userIn){

        User user = new User();
        user.setMail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastName(userIn.getLastname());
        user.setUserName(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try{
            LOG.info("Save user {}" + userIn.getEmail());
            return  userRepository.save(user);

        }catch (Exception ex){
            LOG.error("Error registration:" + ex.getMessage());
            throw new UserExistException(user.getName() + "already exist");
        }

    }

    public User updateUser(UserDTO userDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        user.setName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastname());
        user.setUserName(userDTO.getUsername());
        user.setBio(userDTO.getBio());

        return  userRepository.save(user);
    }

    public User getCurrentUser(Principal principal){
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUserName(username).orElseThrow(()-> new UsernameNotFoundException("Username not found " + username));
    }
}
