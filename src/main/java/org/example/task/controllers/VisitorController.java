package org.example.task.controllers;

import org.example.task.models.AbstractUser;
import org.example.task.models.Event;
import org.example.task.models.Visitor;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/visitor")
public class VisitorController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(VisitorController.class);
    private final EventService eventService;

    @Autowired
    public VisitorController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails user) {
        return "Hello, " + user.getUsername() + "!";
    }

    @GetMapping("/show-events")
    public ResponseEntity<List<Event>> showEvents(@AuthenticationPrincipal UserDetails user) {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/participate/{id}")
    public ResponseEntity<String> participateEvent(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Get request from visitor to participate event: {}", id);
        Optional<Event> eventOpt = eventService.getEventById(id);

        Visitor visitor = userService.getByUsername(userDetails.getUsername());

        if(eventOpt.isEmpty()){
            logger.error("Event with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        }
        Event event = eventOpt.get();

        try{
            eventService.participateInEvent(event, visitor);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            logger.error("Error while participating event", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while participating event");
        }
    }

}
