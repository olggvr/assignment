package org.example.task.services;

import org.example.task.models.Admin;
import org.example.task.models.Contract;
import org.example.task.models.Principal;
import org.example.task.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    public Contract createContract(Admin admin, Principal principal) {
        Contract contract = admin.sendContract(principal);
        return contractRepository.save(contract);
    }

    public void signContract(Principal principal, Contract contract) {
        principal.receiveContract(contract);
        contractRepository.save(contract);
    }

}
