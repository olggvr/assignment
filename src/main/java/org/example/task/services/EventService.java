package org.example.task.services;

import org.example.task.enums.EventStatus;
import org.example.task.models.AbstractUser;
import org.example.task.models.Admin;
import org.example.task.models.Event;
import org.example.task.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;

    private void validateCreationAccessed(){}

    public Event createEvent(String title, AbstractUser admin) {
        validateCreationAccessed();
        Event event = new Event();
        event.setTitle(title);
        event.setCreatedBy((Admin) admin);
        event.setStatus(EventStatus.APPROVED);

        logger.info("Validation passed, creating event: {}", event.getTitle());

        try{
            logger.info("Saving event to database: {}", event.getTitle());
            return eventRepository.save(event);
        }catch (Exception e){
            logger.error("Error creating event: {}", e.getMessage());
            return null;
        }
    }

}
