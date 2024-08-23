package com.example.task_management_system.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import com.example.task_management_system.dto.CreateTaskDto;
import com.example.task_management_system.dto.TaskDto;
import com.example.task_management_system.dto.UpdateTaskDto;
import com.example.task_management_system.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Task methods", description = "Methods for working with tasks")
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/")
    @Operation(summary = "Get a sorted and filtered list of tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks found", content = @Content(schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Incorrect status or priority", content = @Content),
            @ApiResponse(responseCode = "404", description = "No found author or executor", content = @Content)
    })
    public ResponseEntity<?> getAllTasks(
            @Parameter(description = "Page number", example = "0") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(description = "Sort, example: id,asc; status,desc; title.asc") @RequestParam(required = false, defaultValue = "id,asc") String[] sort,
            @Parameter(description = "Title of the task") @RequestParam(required = false) String title,
            @Parameter(description = "Status of the task") @RequestParam(required = false) String status,
            @Parameter(description = "Priority of the task") @RequestParam(required = false) String priority,
            @Parameter(description = "Executor email") @RequestParam(required = false) String executorEmail,
            @Parameter(description = "Author email") @RequestParam(required = false) String authorEmail) {
        Pageable pageable = createPageable(page, size, sort);
        return taskService.findFilteredTasks(title, status, priority, executorEmail, authorEmail, pageable);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get task by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found", content = @Content(schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    public ResponseEntity<?> getTask(@Parameter(description = "Task ID") @PathVariable Long taskId) {
        return taskService.findTask(taskId);
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new task", description = "Create a new task with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created", content = @Content(schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<?> createTask(
            @Parameter(description = "Task details to create") @RequestBody CreateTaskDto createTaskDto,
            Principal principal) {
        return taskService.saveTask(createTaskDto, principal);
    }

    @PutMapping("/{taskId}")
    @Operation(summary = "Update an existing task", description = "Update an existing task with the provided details. "
            + "Author can completely update the task. Executor can only change the status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated", content = @Content(schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status input", content = @Content),
            @ApiResponse(responseCode = "403", description = "User not allowed to update this task", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody UpdateTaskDto updateTaskDto,
            Principal principal) {
        return taskService.updateTask(taskId, updateTaskDto, principal);
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete a task", description = "Delete a task by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted", content = @Content),
            @ApiResponse(responseCode = "403", description = "User not allowed to delete this task", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId, Principal principal) {
        return taskService.deleteTask(taskId, principal);
    }

    
    private Pageable createPageable(int page, int size, String[] sort) {
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        return PageRequest.of(page, size, direction, sort[0]);
    }
}
