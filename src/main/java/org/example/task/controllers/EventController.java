package org.example.task.controllers;

import org.example.task.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class EventController {

    @Autowired
    private EventService eventService;

    

}
