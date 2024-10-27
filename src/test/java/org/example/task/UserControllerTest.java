package org.example.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.task.models.User;
import org.example.task.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testRegisterUser() throws Exception {
        User testUser = new User();
        testUser.setUsername("newuser");
        testUser.setPassword("password");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newuser"));
    }

    @Test
    void testDeleteUser() throws Exception {
        User savedUser = new User();
        savedUser.setUsername("deleteuser");
        savedUser.setPassword("password");
        savedUser = userRepository.save(savedUser);

        mockMvc.perform(delete("/users/" + savedUser.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteNonExistingUser() throws Exception {
        mockMvc.perform(delete("/users/999"))
                .andExpect(status().isNotFound());
    }

}
