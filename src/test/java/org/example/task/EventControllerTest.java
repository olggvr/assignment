package org.example.task;

import org.example.task.controllers.EventController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(controllers = EventController.class)
public class EventControllerTest {
}
