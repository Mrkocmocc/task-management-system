package com.example.task_management_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Schema(description = "Task Data Transfer Object")
@Getter
@Setter
public class TaskDto {
    @Schema(description = "Task ID", example = "1")
    @JsonProperty(required = false)
    private Long id;

    @Schema(description = "Task title", example = "Task 1")
    @JsonProperty(required = false)
    private String title;

    @Schema(description = "Task description", example = "Description for Task 1")
    @JsonProperty(required = false)
    private String description;
    
    @Schema(description = "Task status", example = "TODO")
    @JsonProperty(required = false)
    private String status;

    @Schema(description = "Task priority", example = "HIGH")
    @JsonProperty(required = false)
    private String priority;

    @Schema(description = "Author email", example = "author@example.com")
    @JsonProperty(required = false)
    private String authorEmail;

    @Schema(description = "Executor email", example = "executor@example.com")
    @JsonProperty(required = false)
    private String executorEmail;

    @Schema(description = "Comments on the task")
    @JsonProperty(required = false)
    private List<CommentDto> comments;
}
