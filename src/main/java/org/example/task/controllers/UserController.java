package org.example.task.controllers;

import org.example.task.exceptions.UserNotFoundException;
import org.example.task.models.AbstractUser;
import org.example.task.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(final UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody AbstractUser user) {

        logger.info("Received registration request for user: {}", user.getUsername());
        try{
            this.userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");

        }catch (Exception e){
            logger.error("Error while creating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        logger.info("Received login request for user: {}", username);
        try{
            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(username, password);

            Authentication authentication = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Authentication successful for user: {}", username);
            return ResponseEntity.status(HttpStatus.OK).body("auth successful");
        }catch (Exception e){
            logger.error("Error while authenticating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        logger.info("Received delete request for user: {}", id);

        try{
            userService.deleteUser(id);
            logger.info("User deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        } catch (UserNotFoundException e){
            logger.error("Deleting error: user: {} not found: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found while deleting");
        } catch (Exception e) {
            logger.error("Error while deleting user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user: " + e.getMessage());
        }
    }

}
