package com.example.msuser.service;


import com.example.msuser.entity.User;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendExpiryNotification(User user) {
        System.out.println("📧 Sending expiry notification to user: " + user.getEmail());
    }
}