package com.example.task_management_system.dto;

import lombok.Data;

@Data
public class UpdateTaskDto {
    private String title;
    private String description;
    private String status;
    private String priority;
    private String executorEmail;
}
