package com.example.msuser.controller;

import com.example.msuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cron")
@RequiredArgsConstructor
public class ExpiryController {
    private final UserService userService;

    @PostMapping("/check-user-expiry")
    public ResponseEntity<String> triggerExpiryCheck() {
        userService.checkExpiredUsersAndNotify();
        return ResponseEntity.ok("User expiry check triggered successfully");
    }
}
