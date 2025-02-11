package com.example.Task_Manager.demo.Controller;

import com.example.Task_Manager.demo.CustomExceptions.TaskNotFoundException;
import com.example.Task_Manager.demo.CustomExceptions.UserDoesntExistsException;
import com.example.Task_Manager.demo.DTO.TaskDTO;
import com.example.Task_Manager.demo.Response.UserTasksResponse;
import com.example.Task_Manager.demo.Service.ManagerService;
import com.example.Task_Manager.demo.Service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequiredArgsConstructor
@RequestMapping("/manager")
@RestController
public class ManagerController {
    private final TaskService taskService;
    private final ManagerService managerService;

    public ManagerController(TaskService taskService, ManagerService managerService) {
        this.taskService = taskService;
        this.managerService = managerService;
    }

    @PreAuthorize("hasAuthority('MANAGER)")
    @PostMapping("/create-task")
    public ResponseEntity<String> createTask(@RequestBody TaskDTO taskDto) {
        return new ResponseEntity<>(taskService.createTask(taskDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/assign-task")
    public ResponseEntity<UserTasksResponse> assignTaskToUser(@RequestParam Long userId, @RequestParam Long taskId) {
        try {
            UserTasksResponse response = taskService.assignTaskToUser(userId, taskId);
            return ResponseEntity.ok(response);
        } catch (UserDoesntExistsException | TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/all-users-tasks")
    public ResponseEntity<List<UserTasksResponse>> getAllUsersWithTasks() {
        List<UserTasksResponse> response = managerService.getAllUsersWithTasks();
        return ResponseEntity.ok(response);
    }
}
