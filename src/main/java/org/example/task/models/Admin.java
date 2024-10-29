package org.example.task.models;

public class Admin extends AbstractUser{

    public Admin(String username, String password) {
        super(username, password, Role.ADMIN);
    }

    public void createEvent(){}

    public void sendContract(){}
}
