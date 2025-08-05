package com.wasilewskyy.medical_clinic.controller;

import com.wasilewskyy.medical_clinic.mapper.UserMapper;
import com.wasilewskyy.medical_clinic.dto.CreateUserCommand;
import com.wasilewskyy.medical_clinic.dto.PatientDTO;
import com.wasilewskyy.medical_clinic.dto.UserDTO;
import com.wasilewskyy.medical_clinic.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users", description = "User management API")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Get all users", description = "Returns a list of all users.")
    @ApiResponse(responseCode = "200", description = "List of users",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDTO.class)))
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get user by username", description = "Returns a single user by username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = PatientDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{username}")
    public UserDTO getUser(@PathVariable String username) {
        return userMapper.toDTO(userService.getUser(username));
    }

    @Operation(summary = "Create a new user", description = "Creates a new user in the system.")
    @ApiResponse(responseCode = "201", description = "User created",
            content = @Content(schema = @Schema(implementation = UserDTO.class)))
    @PostMapping
    public void createUser(@RequestBody CreateUserCommand command) {
        userService.addUser(userMapper.toUser(command));
    }

    @Operation(summary = "Delete a user by username", description = "Deletes the user by username.")
    @ApiResponse(responseCode = "204", description = "User deleted")
    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

    @Operation(summary = "Update a user by username", description = "Updates an existing user by username.")
    @ApiResponse(responseCode = "200", description = "User updated",
            content = @Content(schema = @Schema(implementation = PatientDTO.class)))
    @PutMapping("/{username}")
    public void updateUser(@PathVariable String username, @RequestBody CreateUserCommand command) {
        userService.updateUser(username, userMapper.toUser(command));
    }
}
