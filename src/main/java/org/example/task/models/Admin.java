package org.example.task.models;

import jakarta.persistence.Entity;
import org.example.task.enums.Role;
import org.example.task.interfaces.AdminHandles;

@Entity
public class Admin extends AbstractUser implements AdminHandles {

    public Admin() {}

    public Admin(AbstractUser user) {
        super(user.getUsername(), user.getPassword(), Role.ADMIN);
    }

    public void createEvent(){}

    public void sendContract(){}
}
