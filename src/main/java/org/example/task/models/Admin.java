package org.example.task.models;

import jakarta.persistence.Entity;

@Entity
public class Admin extends AbstractUser{

    public Admin() {}

    public Admin(AbstractUser user) {
        super(user.getUsername(), user.getPassword(), Role.ADMIN);
    }

    public void createEvent(){}

    public void sendContract(){}
}
