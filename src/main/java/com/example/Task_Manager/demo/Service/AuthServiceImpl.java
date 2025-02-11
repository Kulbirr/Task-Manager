package com.example.Task_Manager.demo.Service;

import com.example.Task_Manager.demo.Configuration.JWTProvider;
import com.example.Task_Manager.demo.CustomExceptions.InvalidCredentialsException;
import com.example.Task_Manager.demo.DTO.LoginDTO;
import com.example.Task_Manager.demo.Model.User;
import com.example.Task_Manager.demo.Repository.UserRepo;
import com.example.Task_Manager.demo.Response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(LoginDTO loginDto) throws InvalidCredentialsException {
        Optional<User> userOptional = userRepo.findByEmail(loginDto.getEmail());

        if(userOptional.isEmpty()) throw new InvalidCredentialsException("Invalid username.");

        User authenticateUser = userOptional.get();

        if(!passwordEncoder.matches(loginDto.getPassword(), authenticateUser.getPassword())){
            throw new InvalidCredentialsException("Incorrect password.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>(authenticateUser.getAuthorities());

        if (authorities.isEmpty()) {
            throw new IllegalStateException("Ask the admin to assign you a role.");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticateUser.getEmail(), authenticateUser.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JWTProvider.generateToken(authentication);
        System.out.println("Generated JWT Token: " + token);
        AuthResponse authResponse = new AuthResponse(token, authorities.toString(),  "logged in Successfully.");

        return authResponse ;
    }
}
