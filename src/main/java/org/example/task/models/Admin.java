package org.example.task.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.example.task.enums.Role;
import org.example.task.interfaces.AdminHandles;

@Entity
@Table(name = "admins")
public class Admin extends AbstractUser implements AdminHandles {

    public Admin() {}

    public Admin(AbstractUser user) {
        super(user.getUsername(), user.getPassword(), Role.ADMIN);
    }

    public void createEvent(){}

    public Contract sendContract(Principal principal){
        return new Contract(this, principal);
    }
}
