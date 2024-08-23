package com.example.task_management_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CommentDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String content;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto author;
}
