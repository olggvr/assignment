package org.example.task;

import org.example.task.controllers.UserController;
import org.example.task.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testRegisterUser() throws Exception {

        User mockUser = new User();
        mockUser.setUsername("Test");
        mockUser.setPassword("Test");
        Role role = new Role();
        role.setName("visitor");
        mockUser.setRole(role);

        when(userService.registerUser(Mockito.any(User.class))).thenReturn(mockUser);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockUser.toString())
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("Test"));
    }

}
