package com.example.Task_Manager.demo.Service;

import com.example.Task_Manager.demo.CustomExceptions.TaskNotFoundException;
import com.example.Task_Manager.demo.CustomExceptions.UserDoesntExistsException;
import com.example.Task_Manager.demo.DTO.TaskDTO;
import com.example.Task_Manager.demo.Model.Role;
import com.example.Task_Manager.demo.Model.Task;
import com.example.Task_Manager.demo.Model.User;
import com.example.Task_Manager.demo.Repository.TaskRepo;
import com.example.Task_Manager.demo.Repository.UserRepo;
import com.example.Task_Manager.demo.Response.UserTasksResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService{
    private final UserRepo userRepo;
    private final TaskRepo taskRepo;


    @Override
    public String createTask(TaskDTO taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());

        taskRepo.save(task);
        return "Task Created successfully.";
    }


    @Transactional
    @Override
    public UserTasksResponse assignTaskToUser(Long userId, Long taskId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserDoesntExistsException("User not found!"));

        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found!"));

        task.setAssigned_to(user); // Ensure the task knows which user it belongs to
        taskRepo.save(task); // Save the task to persist the user assignment

        user.getTaskList().add(task); // Add task to the user
        userRepo.save(user); // Save the updated user

        return new UserTasksResponse(
                user.getUser_id(),
                user.getName(),
                user.getEmail(),
                user.getRoleList().stream().map(Role::getName).collect(Collectors.toSet()),
                user.getTaskList()
        );
    }


}
