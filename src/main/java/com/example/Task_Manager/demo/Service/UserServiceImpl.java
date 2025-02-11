package com.example.Task_Manager.demo.Service;



import com.example.Task_Manager.demo.CustomExceptions.UserDoesntExistsException;
import com.example.Task_Manager.demo.Model.Role;
import com.example.Task_Manager.demo.Model.User;
import com.example.Task_Manager.demo.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import com.example.Task_Manager.demo.Response.UserTasksResponse;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public UserTasksResponse getUserProfile(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserDoesntExistsException("User not found!"));

        return new UserTasksResponse(
                user.getUser_id(),
                user.getName(),
                user.getEmail(),
                user.getRoleList().stream().map(Role::getName).collect(Collectors.toSet()),
                user.getTaskList()
        );
    }
}
