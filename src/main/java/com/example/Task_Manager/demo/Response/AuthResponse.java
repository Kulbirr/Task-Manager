package com.example.Task_Manager.demo.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private String token;
    private String role;
    private String message;
}
