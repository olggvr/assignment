package org.example.task.services;

import org.example.task.enums.Role;
import org.example.task.exceptions.UserNotFoundException;
import org.example.task.models.AbstractUser;
import org.example.task.models.Admin;
import org.example.task.models.Principal;
import org.example.task.models.Visitor;
import org.example.task.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    private void validateUserData(AbstractUser user) throws IllegalArgumentException{
        String name = user.getUsername();
        String pass = user.getPassword();
        Role role = user.getRole();
        if(name == null || name.isEmpty()){
            logger.error("Validation error in User service: Username is null or empty");
            throw new IllegalArgumentException("Username is null or empty");
        }
        if (getByUsername(name) != null){
            logger.error("Validation error in User service: Username already exists");
            throw new IllegalArgumentException("Username already exists");
        }
        if(pass == null || pass.isEmpty()){
            logger.error("Validation error in User service: Password is null or empty");
            throw new IllegalArgumentException("Password is null or empty");
        }
        if(!role.equals(Role.ADMIN) && !role.equals(Role.VISITOR) && !role.equals(Role.PRINCIPAL)){
            logger.error("Validation error in User service: user role is not valid");
            throw new IllegalArgumentException("user role is not valid");
        }
    }

    private AbstractUser initUser(AbstractUser user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return switch (user.getRole()) {
            case ADMIN -> new Admin(user);
            case VISITOR -> new Visitor(user);
            case PRINCIPAL -> new Principal(user);
        };
    }

    public AbstractUser registerUser(AbstractUser user){
        validateUserData(user);
        AbstractUser iniUser = initUser(user);
        logger.info("User initialized");

        try {
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

    @SuppressWarnings("unchecked")
    public <T extends AbstractUser> T getByUsername(String username) {
        return (T) this.userRepository.findByUsername(username);
    }

}

