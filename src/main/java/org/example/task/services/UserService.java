package org.example.task.services;

import org.example.task.controllers.UserController;
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

    public User registerUser(String username, String password, String roleName) {
        User user = new User();
        try {
            Role role = this.roleRepository.findByName(roleName);
            user.setUsername(username);
            String hashedPassword = this.passwordEncoder.encode(password);
            user.setPassword(hashedPassword);
            user.setRole(role);

            logger.info("User created: {}", username);
        }catch (Exception e) {
            logger.error("Error with user registration: {}", e.getMessage());
        }

        return this.userRepository.save(user);
    }

    public User getByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

}

