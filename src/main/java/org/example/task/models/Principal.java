package org.example.task.models;

import jakarta.persistence.Entity;
import org.example.task.enums.Role;
import org.example.task.interfaces.PrincipalHandles;

@Entity
public class Principal extends AbstractUser implements PrincipalHandles {

    public Principal(){}

    public Principal(AbstractUser user) {
        super(user.getUsername(), user.getPassword(), Role.PRINCIPAL);
    }

    public void receiveContract(Contract contract){
        contract.sign();
    }

}
