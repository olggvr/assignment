package org.example.task.models;

public class Admin extends AbstractUser{

    public Admin(AbstractUser user) {
        super(user.getUsername(), user.getPassword(), Role.ADMIN);
    }

    public void createEvent(){}

    public void sendContract(){}
}
