package org.example.task.controllers;

import org.example.task.models.AbstractUser;
import org.example.task.models.Admin;
import org.example.task.models.Contract;
import org.example.task.models.Principal;
import org.example.task.repositories.ContractRepository;
import org.example.task.repositories.UserRepository;
import org.example.task.services.ContractService;
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

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;
    private final UserService userService;
    private final ContractService contractService;
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;

    @Autowired
    public EventController(EventService eventService, UserService userService, ContractRepository contractRepository, ContractService contractService, UserRepository userRepository) {
        this.eventService = eventService;
        this.userService = userService;
        this.contractService = contractService;
        this.userRepository = userRepository;
        this.contractRepository  = contractRepository;
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails user) {
        return "Hello admin " + user.getUsername();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEvent(@RequestParam String title,
                                               @AuthenticationPrincipal UserDetails userDetails){
        logger.info("Get request on event creation: {}", title);
        try{
            this.eventService.createEvent(title, userService.getByUsername(userDetails.getUsername()));
            logger.info("Event created successfully: {}", title);
            return ResponseEntity.ok("Event created successfully");
        }catch (IllegalStateException e){
            logger.error("Event creation failed, creation not accepted: {}", e.getMessage());
            logger.info("Redirecting user to home page");
            return ResponseEntity
                    .status(HttpStatus.MOVED_PERMANENTLY)
                    .header("Location", "/home")
                    .build();
        }
        catch (Exception e) {
            logger.error("Error creating event {}: {}", title, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendContract(@RequestParam Long adminId, @RequestParam Long principalId){
        Optional<AbstractUser> adminOpt = userRepository.findById(adminId);
        Optional<AbstractUser> principalOpt = userRepository.findById(principalId);

        if (adminOpt.isEmpty() || principalOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        AbstractUser admin = adminOpt.get();
        AbstractUser principal = principalOpt.get();

        if (!(admin instanceof Admin) || !(principal instanceof Principal)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Contract contract = new Contract((Admin) admin, (Principal) principal);
        contractRepository.save(contract);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
