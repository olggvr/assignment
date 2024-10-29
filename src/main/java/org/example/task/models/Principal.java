package org.example.task.models;

import jakarta.persistence.Entity;

@Entity
public class Principal extends AbstractUser{

    public Principal(){}

    public Principal(AbstractUser user) {
        super(user.getUsername(), user.getPassword(), Role.PRINCIPAL);
    }

    public void receiveContract(){}

    private void acceptContract(){}

    public void makeResponse(){}

}
