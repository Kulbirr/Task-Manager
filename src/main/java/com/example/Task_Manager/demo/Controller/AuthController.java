package com.example.Task_Manager.demo.Controller;

import com.example.Task_Manager.demo.CustomExceptions.InvalidCredentialsException;
import com.example.Task_Manager.demo.DTO.LoginDTO;
import com.example.Task_Manager.demo.Response.AuthResponse;
import com.example.Task_Manager.demo.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDto) {
//        System.out.println("Login method executed for: " + loginDto.getEmail());
        try {
            AuthResponse response = authService.login(loginDto);
            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
