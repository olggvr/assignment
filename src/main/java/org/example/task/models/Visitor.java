package org.example.task.models;

import jakarta.persistence.Entity;

@Entity
public class Visitor extends AbstractUser{

    public Visitor(){}

    public Visitor(AbstractUser user) {
        super(user.getUsername(), user.getPassword(), Role.VISITOR);
    }

    public void checkEvents(){}

    public void participateEvent(){}

}
