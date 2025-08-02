package com.example.msuser.service;

import com.example.msuser.entity.User;
import com.example.msuser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getByName(String name){
        return userRepository.findByName(name);
    }

    public void checkExpiredUsersAndNotify() {
        LocalDateTime thresholdDate = LocalDateTime.now().minusMinutes(1L);

        List<User> expiredUsers = userRepository.findByLastActiveAtBefore(thresholdDate);

        expiredUsers.forEach(notificationService::sendExpiryNotification);

        System.out.println("📢 Expiry check done at " + LocalDateTime.now()
                + ", notified users count: " + expiredUsers.size());
    }
}
