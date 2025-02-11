package com.example.Task_Manager.demo.Service;

import com.example.Task_Manager.demo.CustomExceptions.RoleDoesntExistsException;
import com.example.Task_Manager.demo.CustomExceptions.UserAlreadyExistsException;
import com.example.Task_Manager.demo.CustomExceptions.UserDoesntExistsException;
import com.example.Task_Manager.demo.DTO.UserDto;
import com.example.Task_Manager.demo.Response.AuthResponse;
import com.example.Task_Manager.demo.Response.UserTasksResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserTasksResponse getUserProfile(String email);
}
