package org.example.task.services;

import org.example.task.enums.EventStatus;
import org.example.task.models.AbstractUser;
import org.example.task.models.Admin;
import org.example.task.models.Contract;
import org.example.task.models.Event;
import org.example.task.repositories.ContractRepository;
import org.example.task.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ContractRepository contractRepository;

    private void validateCreationAccessed(Admin admin){
        Contract activeContract = contractRepository.findActiveContractByAdmin(admin);
        if(activeContract == null || !activeContract.isSigned()){
            logger.error("Contract is not signed, event can not be created");
            throw  new IllegalStateException("Contract is not signed, event can not be created");
        }
    }

    public Event createEvent(String title, Admin admin) {
        validateCreationAccessed(admin);
        Event event = new Event();
        event.setTitle(title);
        event.setCreatedBy(admin);
        event.setStatus(EventStatus.APPROVED);

        logger.info("Validation passed, creating event: {}", event.getTitle());
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

}
