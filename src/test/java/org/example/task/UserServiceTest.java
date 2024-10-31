package org.example.task;

import org.example.task.enums.Role;
import org.example.task.exceptions.UserNotFoundException;
import org.example.task.models.AbstractUser;
import org.example.task.models.Admin;
import org.example.task.models.Visitor;
import org.example.task.repositories.UserRepository;
import org.example.task.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private AbstractUser testUser;

    @BeforeEach
    void setUp() {
        testUser = new Visitor();
        testUser.setUsername("testUser");
        testUser.setPassword("password123");
        testUser.setRole(Role.VISITOR);
    }

    @Test
    public void testRegisterUser_Success() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(AbstractUser.class))).thenReturn(testUser);

        AbstractUser registeredUser = userService.registerUser(testUser);

        assertNotNull(registeredUser);
        assertEquals("testUser", registeredUser.getUsername());
        assertEquals("encodedPassword", registeredUser.getPassword());
        verify(userRepository, times(1)).save(any(AbstractUser.class));
    }

    @Test
    public void testRegisterUser_UsernameExists() {
        when(userRepository.findByUsername("testUser")).thenReturn(testUser);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> userService.registerUser(testUser)
        );

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any(AbstractUser.class));
    }

    @Test
    public void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetByUsername_UserFound() {
        when(userRepository.findByUsername("testUser")).thenReturn(testUser);

        AbstractUser foundUser = userService.getByUsername("testUser");

        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getUsername());
    }

    @Test
    public void testGetByUsername_UserNotFound() {
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(null);

        AbstractUser foundUser = userService.getByUsername("nonExistentUser");

        assertNull(foundUser);
    }
}

