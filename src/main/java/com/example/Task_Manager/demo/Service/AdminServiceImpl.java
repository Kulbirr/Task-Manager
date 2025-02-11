package com.example.Task_Manager.demo.Service;

import com.example.Task_Manager.demo.CustomExceptions.RoleDoesntExistsException;
import com.example.Task_Manager.demo.CustomExceptions.UserAlreadyExistsException;
import com.example.Task_Manager.demo.CustomExceptions.UserDoesntExistsException;
import com.example.Task_Manager.demo.DTO.UserDto;
import com.example.Task_Manager.demo.Model.Role;
import com.example.Task_Manager.demo.Model.User;
import com.example.Task_Manager.demo.Repository.RoleRepo;
import com.example.Task_Manager.demo.Repository.UserRepo;
import com.example.Task_Manager.demo.Response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService{

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse createUser(UserDto userDto) throws UserAlreadyExistsException, RoleDoesntExistsException {
        Optional<User> userOptional = userRepo.findByEmail(userDto.getEmail());

//        if user already exists we throw error.
        if(!userOptional.isEmpty()){
            throw new UserAlreadyExistsException("User already exists.");
        }

        User user = new User();

//        converting String of roles to Role and if role doesn't exist throw error.
        Set<Role> roleSet = null;
        if(userDto.getRoles() != null){
            roleSet = userDto.getRoles()
                    .stream()
                    .map(roleName -> roleRepo.findByName(roleName.toUpperCase()).orElseThrow(() -> new RoleDoesntExistsException("Role doesn't exists: "+ roleName)))
                    .collect(Collectors.toSet());
        }

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRoleList(roleSet);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepo.save(user);

        return new AuthResponse("token: "+ null, userDto.getRoles().toString(), "User created successfully!");
    }

    @Override
    public String updateUser(long user_id, UserDto userDto) {
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new UserDoesntExistsException("User not found."));

        // Update only if the new value is provided (non-null)
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Ensure password is encrypted
        }

        if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
            Set<Role> roleSet = userDto.getRoles().stream()
                    .map(roleName -> roleRepo.findByName(roleName.toUpperCase())
                            .orElseThrow(() -> new RoleDoesntExistsException("Role doesn't exist: " + roleName)))
                    .collect(Collectors.toSet());
            user.setRoleList(roleSet);
        }

        // Save the updated user
        userRepo.save(user);
        return "User updated successfully!";
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userList = userRepo.findAll().stream()
                .map(user -> new UserDto(user.getName(),user.getEmail(), null, user.getRoleList()
                        .stream().map(Role::getName).collect(Collectors.toSet())))
                .collect(Collectors.toList());

        return userList;
    }

    @Override
    public UserDto getUserById(long id) throws UserDoesntExistsException {
        User user = userRepo.findById(id).orElseThrow(() -> new UserDoesntExistsException("User doesn't exist for this id: "+ id));
        UserDto userDto = new UserDto(user.getName(), user.getEmail(), null, user.getRoleList().stream().map(Role::getName).collect(Collectors.toSet()));

        return userDto;
    }

    @Override
    public String deleteUser(long userId) throws UserDoesntExistsException {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserDoesntExistsException("User doesn't exist for this id: "+ userId));

        userRepo.delete(user);

        return "User with given userId: "+ "'"+ userId + "'"+ " has been successfully deleted.";
    }

    @Override
    public String assignRole(Long userId, String roleName) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserDoesntExistsException("User not found."));

        Role role = roleRepo.findByName(roleName.toUpperCase())
                .orElseThrow(() -> new RoleDoesntExistsException("Role doesn't exist: " + roleName));

        user.getRoleList().add(role);
        userRepo.save(user);
        return "Role assigned successfully!";
    }
}
