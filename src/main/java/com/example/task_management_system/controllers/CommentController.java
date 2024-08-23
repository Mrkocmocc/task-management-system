package com.example.task_management_system.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import com.example.task_management_system.dto.CommentDto;
import com.example.task_management_system.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Comments methods", description = "Methods for working with comments")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{taskId}")
    @Operation(summary = "Create a comment for a task", description = "Create a new comment for a specific task by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created", content = @Content(schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "Content cannot be empty", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    public ResponseEntity<?> createCommentByTaskId(@PathVariable Long taskId,
            @Parameter(description = "Comment details to create") @RequestBody CommentDto commentDto,
            Principal principal) {
        return commentService.saveComment(taskId, commentDto, principal);
    }
}
