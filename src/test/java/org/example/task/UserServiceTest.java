package org.example.task;

import org.example.task.exceptions.UserNotFoundException;
import org.example.task.models.Role;
import org.example.task.models.User;
import org.example.task.repositories.UserRepository;
import org.example.task.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        Role testRole = new Role();
        testRole.setName("admin");

        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        testUser.setRole(testRole);

        userRepository.deleteAll();
    }

    @Test
    void testRegisterUser() {
        User savedUser = userService.registerUser(testUser);
        Assertions.assertNotNull(savedUser, "User should not be null");
        Assertions.assertEquals("testUser", savedUser.getUsername());
    }

    @Test
    void testDeleteUser() {
        User savedUser = userService.registerUser(testUser);
        Long userId = savedUser.getId();
        userService.deleteUser(userId);
        Assertions.assertFalse(userRepository.existsById(userId), "User should not exist");
    }

    @Test
    void testNonExistUser() {
        long userId = 999L;
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));
    }
}
