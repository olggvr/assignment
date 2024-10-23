package org.example.task.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping(value = "/create")
    public String createUser() {
        return "User created successfully";
    }

}
