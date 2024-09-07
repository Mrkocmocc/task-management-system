package com.example.task_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Data Transfer Object for user registration")
@Getter
@Setter
@AllArgsConstructor
public class RegistrationUserDto {
    @Schema(description = "Email of the user", example = "user@example.com")
    private String email;
    @Schema(description = "Password of the user", example = "password123")
    private String password;
    @Schema(description = "Confirm password of the user", example = "password123")
    private String confirmPassword;
}
