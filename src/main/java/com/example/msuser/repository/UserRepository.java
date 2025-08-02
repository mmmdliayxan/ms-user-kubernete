package com.example.msuser.repository;

import com.example.msuser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
    List<User> findByLastActiveAtBefore(LocalDateTime dateTime);

}

