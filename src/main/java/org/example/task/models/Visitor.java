package org.example.task.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.example.task.enums.Role;
import org.example.task.interfaces.VisitorHandles;

@Entity
@Table(name = "visitors")
public class Visitor extends AbstractUser implements VisitorHandles {

    public Visitor(){}

    public Visitor(AbstractUser user) {
        super(user.getUsername(), user.getPassword(), Role.VISITOR);
    }

    public void checkEvents(){}

    public void participateEvent(){}

}
