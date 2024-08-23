package com.example.task_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationUserDto {
    private String email;
    private String password;
    private String confirmPassword;
}
