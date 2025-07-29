package com.example.msuser.service;

import com.example.msuser.entity.User;
import com.example.msuser.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mockları aktiv edir
        userService = new UserService(userRepository);
    }

    @Test
    void testGetAllUsers() {
        List<User> mockUsers = Arrays.asList(
                new User(1L, "Alice", "alice@example.com"),
                new User(2L, "Bob", "bob@example.com")
        );

        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testCreateUser() {
        User user = new User(null, "Charlie", "charlie@example.com");
        User savedUser = new User(3L, "Charlie", "charlie@example.com");

        when(userRepository.save(user)).thenReturn(savedUser);

        User result = userService.createUser(user);

        assertNotNull(result.getId());
        assertEquals("Charlie", result.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetByIdFound() {
        User user = new User(1L, "Alice", "alice@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getById(1L);

        assertEquals("Alice", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getById(99L);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(99L);
    }
}
