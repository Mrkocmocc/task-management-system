package com.example.task_management_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Update Task Data Transfer Object")
@Getter
@Setter
public class UpdateTaskDto {
    @Schema(description = "Title", example = "Example title")
    private String title;

    @Schema(description = "Description", example = "Example description")
    private String description;

    @Schema(description = "Status", example = "IN_PROGRESS")
    private String status;

    @Schema(description = "Priority", example = "HIGH")
    private String priority;

    @Schema(description = "Executor's email", example = "executor@example.com")
    private String executorEmail;
}
