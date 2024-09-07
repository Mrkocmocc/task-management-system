package com.example.task_management_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Comment Data Transfer Object")
@Getter
@Setter
public class CommentDto {
    @Schema(description = "Comment ID", example = "1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @Schema(description = "Comment content", example = "This is a comment")
    private String content;
    @Schema(description = "Author")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto author;
}
