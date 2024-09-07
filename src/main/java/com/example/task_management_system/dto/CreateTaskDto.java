package com.example.task_management_system.dto;

import com.example.task_management_system.entity.TaskPriority;
import com.example.task_management_system.entity.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "DTO for creating a new task")
@Getter
@Setter
public class CreateTaskDto {
    @Schema(description = "Title of the task", example = "Task 1")
    private String title;

    @Schema(description = "Description of the task", example = "This is a sample task")
    private String description;

    @Schema(description = "Status of the task", example = "IN_PROGRESS")
    @JsonProperty(required = false)
    private TaskStatus status;

    @Schema(description = "Priority of the task", example = "HIGH")
    @JsonProperty(required = false)
    private TaskPriority priority;
}
