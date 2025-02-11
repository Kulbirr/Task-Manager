package com.example.Task_Manager.demo.Service;

import com.example.Task_Manager.demo.Response.UserTasksResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ManagerService {
    List<UserTasksResponse> getAllUsersWithTasks();
}
