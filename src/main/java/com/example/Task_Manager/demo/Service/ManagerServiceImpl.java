package com.example.Task_Manager.demo.Service;

import com.example.Task_Manager.demo.Model.Role;
import com.example.Task_Manager.demo.Model.User;
import com.example.Task_Manager.demo.Repository.UserRepo;
import com.example.Task_Manager.demo.Response.UserTasksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ManagerServiceImpl implements ManagerService{
    private final UserRepo userRepo;

    @Override
    public List<UserTasksResponse> getAllUsersWithTasks() {
        List<User> users = userRepo.findAll();

        return users.stream().map(user -> new UserTasksResponse(
                user.getUser_id(),
                user.getName(),
                user.getEmail(),
                user.getRoleList().stream().map(Role::getName).collect(Collectors.toSet()),
                user.getTaskList()
        )).collect(Collectors.toList());
    }
}
