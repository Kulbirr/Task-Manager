package com.example.Task_Manager.demo.Response;

import com.example.Task_Manager.demo.Model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserTasksResponse {
    private Long userId;
    private String name;
    private String email;
    private Set<String> roles;
    private List<Task> tasks; // Assuming you have a Task entity
}