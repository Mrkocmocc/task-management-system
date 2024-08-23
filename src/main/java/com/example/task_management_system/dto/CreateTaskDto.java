package com.example.task_management_system.dto;

import com.example.task_management_system.entity.TaskPriority;
import com.example.task_management_system.entity.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateTaskDto {
    private String title;
    private String description;

    @JsonProperty(required = false)
    private TaskStatus status;

    @JsonProperty(required = false)
    private TaskPriority priority;
}
