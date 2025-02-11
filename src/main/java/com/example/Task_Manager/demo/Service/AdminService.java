package com.example.Task_Manager.demo.Service;

import com.example.Task_Manager.demo.CustomExceptions.RoleDoesntExistsException;
import com.example.Task_Manager.demo.CustomExceptions.UserAlreadyExistsException;
import com.example.Task_Manager.demo.CustomExceptions.UserDoesntExistsException;
import com.example.Task_Manager.demo.DTO.UserDto;
import com.example.Task_Manager.demo.Response.AuthResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    AuthResponse createUser(UserDto userDto) throws UserAlreadyExistsException, RoleDoesntExistsException;

    String updateUser(long user_id, UserDto userDto) throws RoleDoesntExistsException;

    List<UserDto> getAllUsers();

    UserDto getUserById(long id) throws UserDoesntExistsException;

    String deleteUser(long userId) throws UserDoesntExistsException;

    String assignRole(Long userId, String roleName);
}
