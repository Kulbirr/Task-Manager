package com.example.Task_Manager.demo.Service;

import com.example.Task_Manager.demo.CustomExceptions.InvalidCredentialsException;
import com.example.Task_Manager.demo.DTO.LoginDTO;
import com.example.Task_Manager.demo.Response.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    AuthResponse login(LoginDTO loginDto) throws InvalidCredentialsException;
}
