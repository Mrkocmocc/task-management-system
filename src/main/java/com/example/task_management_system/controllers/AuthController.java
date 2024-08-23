package com.example.task_management_system.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_management_system.dto.JwtRequest;
import com.example.task_management_system.dto.RegistrationUserDto;
import com.example.task_management_system.dto.UserDto;
import com.example.task_management_system.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.Principal;

import lombok.RequiredArgsConstructor;

@Tag(name = "Auth methods", description = "Methods for authentication")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login user and get JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = JwtRequest.class))),
            @ApiResponse(responseCode = "400", description = "Invalid email or password", content = @Content)
    })
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Passwords do not match or email already exists", content = @Content)
    })
    public ResponseEntity<?> createNewUser(@Parameter(description = "User data for registration") @RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @GetMapping("/")
    @Operation(summary = "Get current user info")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    public ResponseEntity<?> home(Principal principal) {
        return ResponseEntity.ok(principal);
    }
}
