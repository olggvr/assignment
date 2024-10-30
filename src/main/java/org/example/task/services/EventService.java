package org.example.task.services;

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

    public Event createEvent(Event event) {
        validateCreationAccessed();
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
