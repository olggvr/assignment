package org.example.task.controllers;

import org.example.task.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visitor")
public class VisitorController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(VisitorController.class);

    @Autowired
    public VisitorController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails user) {
        return "Hello, " + user.getUsername() + "!";
    }

}
