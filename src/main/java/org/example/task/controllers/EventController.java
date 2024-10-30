package org.example.task.controllers;

import org.example.task.repositories.UserRepository;
import org.example.task.services.EventService;
import org.example.task.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService, UserRepository userRepository) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @PostMapping("/create")
    private ResponseEntity<String> createEvent(@RequestParam String title,
                                               @AuthenticationPrincipal UserDetails userDetails){
        logger.info("Get request on event creation: {}", title);
        try{
            this.eventService.createEvent(title, userService.getByUsername(userDetails.getUsername()));
            logger.info("Event created successfully: {}", title);
            return ResponseEntity.ok("Event created successfully");
        } catch (Exception e) {
            logger.error("Error creating event {}: {}", title, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
