package com.example.Task_Manager.demo.Controller;

import com.example.Task_Manager.demo.Response.UserTasksResponse;
import com.example.Task_Manager.demo.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserTasksResponse> getUserProfile(Authentication authentication) {
        String email = authentication.getName();
        UserTasksResponse response = userService.getUserProfile(email);
        return ResponseEntity.ok(response);
    }

}
