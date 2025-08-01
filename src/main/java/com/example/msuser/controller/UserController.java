package com.example.msuser.controller;


import com.example.msuser.entity.User;
import com.example.msuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/by-name")
    public User getByName(@RequestParam String name) {
        return userService.getByName(name);
    }

    @GetMapping("/hello")
    public String getHello(){
        return "Hello world";
    }

    @GetMapping("/test")
    public String test(){
        return "Hello matrix";
    }

    @GetMapping("/test3")
    public String test3(){
        return "Hello matrix, hello kubernete";
    }

    @PostMapping("/check-user-expiry")
    public ResponseEntity<String> triggerExpiryCheck() {
        userService.checkExpiredUsersAndNotify();
        return ResponseEntity.ok("User expiry check triggered successfully");
    }

}
