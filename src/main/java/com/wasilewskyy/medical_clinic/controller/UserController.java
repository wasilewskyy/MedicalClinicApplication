package com.wasilewskyy.medical_clinic.controller;

import com.wasilewskyy.medical_clinic.mapper.UserMapper;
import com.wasilewskyy.medical_clinic.model.CreateUserCommand;
import com.wasilewskyy.medical_clinic.model.UserDTO;
import com.wasilewskyy.medical_clinic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public void createUser(@RequestBody CreateUserCommand command) {
        userService.addUser(userMapper.toUser(command));
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

    @PutMapping("/{username}")
    public void updateUser(@PathVariable String username, @RequestBody CreateUserCommand command) {
        userService.updateUser(username, userMapper.toUser(command));
    }

    @GetMapping("/{username}")
    public UserDTO getUser(@PathVariable String username) {
        return userMapper.toDTO(userService.getUser(username));
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}
