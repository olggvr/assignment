package org.example.task.controllers;

import org.example.task.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void createUser(@RequestBody String username, String password, String role) {
        try{

            this.userService.registerUser(username, password, role);

        }catch (Exception e){
            logger.error("Error while creating user: {}", e.getMessage());
        }
    }

}
