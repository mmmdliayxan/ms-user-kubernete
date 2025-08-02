package com.example.msuser.service;

import com.example.msuser.entity.User;
import com.example.msuser.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    private UserService userExpiryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userExpiryService = new UserService(userRepository, notificationService);
    }

    @Test
    void testCheckExpiredUsersAndNotify_WithExpiredUsers() {
        // Arrange
        LocalDateTime expiryThreshold = LocalDateTime.now().minusDays(90);
        User user1 = new User();
        user1.setEmail("user1@example.com");
        user1.setLastActiveAt(expiryThreshold.minusDays(1));

        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setLastActiveAt(expiryThreshold.minusDays(5));

        List<User> expiredUsers = List.of(user1, user2);

        when(userRepository.findByLastActiveAtBefore(any(LocalDateTime.class)))
                .thenReturn(expiredUsers);

        // Act
        userExpiryService.checkExpiredUsersAndNotify();

        // Assert
        verify(userRepository, times(1)).findByLastActiveAtBefore(any(LocalDateTime.class));
        verify(notificationService, times(2)).sendExpiryNotification(any(User.class));
    }

    @Test
    void testCheckExpiredUsersAndNotify_NoExpiredUsers() {
        // Arrange
        when(userRepository.findByLastActiveAtBefore(any(LocalDateTime.class)))
                .thenReturn(List.of());

        // Act
        userExpiryService.checkExpiredUsersAndNotify();

        // Assert
        verify(userRepository, times(1)).findByLastActiveAtBefore(any(LocalDateTime.class));
        verify(notificationService, never()).sendExpiryNotification(any());
    }
}
