package org.example.task.models;

public class Principal extends AbstractUser{

    public Principal(AbstractUser user) {
        super(user.getUsername(), user.getPassword(), Role.PRINCIPAL);
    }

    public void receiveContract(){}

    private void acceptContract(){}

    public void makeResponse(){}

}
