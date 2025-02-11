package com.example.Task_Manager.demo.Controller;

import com.example.Task_Manager.demo.CustomExceptions.RoleDoesntExistsException;
import com.example.Task_Manager.demo.CustomExceptions.UserAlreadyExistsException;
import com.example.Task_Manager.demo.CustomExceptions.UserDoesntExistsException;
import com.example.Task_Manager.demo.DTO.UserDto;
import com.example.Task_Manager.demo.Response.AuthResponse;
import com.example.Task_Manager.demo.Service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/")
//@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("create-user")
    public ResponseEntity<AuthResponse> createUser(@Valid @RequestBody UserDto userDto){
        try{
            AuthResponse response = adminService.createUser(userDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (UserAlreadyExistsException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (RoleDoesntExistsException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update-user/{user_id}")
    public ResponseEntity<String> updateUser(@PathVariable long user_id, @RequestBody UserDto userDto){
        try{
            String response = adminService.updateUser(user_id, userDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (RoleDoesntExistsException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get-users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = adminService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("get-user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        try {
            UserDto user = adminService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserDoesntExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("delete-user")
    public ResponseEntity<String> deleteUser(@RequestParam long user_id){
        try{
            String response = adminService.deleteUser(user_id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (UserDoesntExistsException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("assign-role/{userId}/{roleName}")
    public ResponseEntity<String> assignRoleToUser(@PathVariable Long userId, @PathVariable String roleName) {
        try {
            String response = adminService.assignRole(userId, roleName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
