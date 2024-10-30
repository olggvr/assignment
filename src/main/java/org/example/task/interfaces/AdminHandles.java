package org.example.task.interfaces;

import org.example.task.models.Contract;
import org.example.task.models.Principal;

public interface AdminHandles {

    void createEvent();
    Contract sendContract(Principal principal);

}
