package org.example.task.controllers;

import org.example.task.models.AbstractUser;
import org.example.task.models.Admin;
import org.example.task.models.Contract;
import org.example.task.models.Principal;
import org.example.task.repositories.ContractRepository;
import org.example.task.repositories.UserRepository;
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
@RequestMapping("/principal")
public class PrincipalController {

    private static final Logger logger = LoggerFactory.getLogger(PrincipalController.class);
    private final UserService userService;
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;

    @Autowired
    public PrincipalController(UserService userService, UserRepository userRepository, ContractRepository contractRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.contractRepository = contractRepository;
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails user) {
        return "Hello, principal page: " + user.getUsername() + "!";
    }

    @PostMapping("/sign")
    public ResponseEntity<Void> signContract(@RequestParam Long adminId, @RequestParam Long principalId){

        Optional<AbstractUser> adminOpt = userRepository.findById(adminId);
        Optional<AbstractUser> principalOpt = userRepository.findById(principalId);

        if(adminOpt.isEmpty() || principalOpt.isEmpty()){
            logger.error("Admin or principal not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        AbstractUser admin = adminOpt.get();
        AbstractUser principal = principalOpt.get();

        if (!(admin instanceof Admin) || !(principal instanceof Principal)) {
            logger.error("Bad request: admin or principal values are not validatable");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<Contract> contractOpt = contractRepository.findByAdminAndPrincipal((Admin) admin, (Principal) principal);
        if (contractOpt.isEmpty()) {
            logger.error("Error: contract not found, nothing to sign");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Contract contract = contractOpt.get();
        if (contract.isSigned()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        contract.sign();
        contractRepository.save(contract);

        return ResponseEntity.ok().build();
    }

}
