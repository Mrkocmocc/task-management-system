package com.example.task_management_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;

@Data
public class TaskDto {
    @JsonProperty(required = false)
    private Long id;
    @JsonProperty(required = false)
    private String title;
    @JsonProperty(required = false)
    private String description;
    @JsonProperty(required = false)
    private String status;
    @JsonProperty(required = false)
    private String priority;
    @JsonProperty(required = false)
    private String authorEmail;
    @JsonProperty(required = false)
    private String executorEmail;
    @JsonProperty(required = false)
    private List<CommentDto> comments;
}
