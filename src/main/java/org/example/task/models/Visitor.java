package org.example.task.models;

public class Visitor extends AbstractUser{

    public Visitor(AbstractUser user) {
        super(user.getUsername(), user.getPassword(), Role.VISITOR);
    }

    public void checkEvents(){}

    public void participateEvent(){}

}
