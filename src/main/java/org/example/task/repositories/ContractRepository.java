package org.example.task.repositories;

import org.example.task.models.Admin;
import org.example.task.models.Contract;
import org.example.task.models.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Contract findActiveContractByAdmin(Admin admin);

    Optional<Contract> findByAdminAndPrincipal(Admin admin, Principal principal);

}
