package com.example.Task_Manager.demo.Service;

import com.example.Task_Manager.demo.DTO.TaskDTO;
import com.example.Task_Manager.demo.Model.Task;
import com.example.Task_Manager.demo.Response.UserTasksResponse;

public interface TaskService {
    String createTask(TaskDTO taskDto);
    UserTasksResponse assignTaskToUser(Long userId, Long taskId);


}
