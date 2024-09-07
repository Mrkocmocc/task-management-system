package com.example.task_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "User Data Transfer Object")
@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    @Schema(description = "User ID", example = "1")
    private long id;
    @Schema(description = "User email", example = "user@example.com")
    private String email;
}
