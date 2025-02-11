package com.example.Task_Manager.demo.Repository;

import com.example.Task_Manager.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
}
