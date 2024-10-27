package org.example.task.services;

import org.example.task.exceptions.UserNotFoundException;
import org.example.task.models.Role;
import org.example.task.models.User;
import org.example.task.repositories.RoleRepository;
import org.example.task.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    private void validateUserData(String username, String password, String roleName) throws IllegalArgumentException{
        if(username == null || username.isEmpty()){
            logger.error("Validation error in User service: Username is null or empty");
            throw new IllegalArgumentException("Username is null or empty");
        }
        if(password == null || password.isEmpty()){
            logger.error("Validation error in User service: Password is null or empty");
            throw new IllegalArgumentException("Password is null or empty");
        }
        if(roleName == null || roleName.isEmpty()){
            logger.error("Validation error in User service: Role name is null or empty");
            throw new IllegalArgumentException("Role name is null or empty");
        }
    }

    private User initUser(String username, String password, String roleName){

        User user = new User();
        user.setUsername(username);
        String hashedPassword = this.passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        Role role = this.roleRepository.findByName(roleName);
        user.setRole(role);

        return user;
    }

    public User registerUser(User user){
        validateUserData(user.getUsername(), user.getPassword(), user.getRole().getName());
        User iniUser = initUser(user.getUsername(), user.getPassword(), user.getRole().getName());
        logger.info("User initialized");

        try {
            logger.info("User saved to database: {}, id: {}", iniUser.getUsername(), iniUser.getId());
            return this.userRepository.save(iniUser);
        }catch (Exception e) {
            logger.error("Error with saving user to database: {}", e.getMessage());
            return null;
        }
    }

    public void deleteUser(long id){
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    public User getByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

}

