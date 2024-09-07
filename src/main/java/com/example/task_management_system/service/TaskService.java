package com.example.task_management_system.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.task_management_system.dto.CommentDto;
import com.example.task_management_system.dto.CreateTaskDto;
import com.example.task_management_system.dto.TaskDto;
import com.example.task_management_system.dto.UpdateTaskDto;
import com.example.task_management_system.entity.Comment;
import com.example.task_management_system.entity.Task;
import com.example.task_management_system.entity.TaskPriority;
import com.example.task_management_system.entity.TaskStatus;
import com.example.task_management_system.entity.User;
import com.example.task_management_system.exceptions.AppError;
import com.example.task_management_system.mapping.CommentMapping;
import com.example.task_management_system.mapping.TaskMapping;
import com.example.task_management_system.mapping.UserMapping;
import com.example.task_management_system.repositories.TaskRepository;

import jakarta.transaction.Transactional;

import java.security.Principal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final CommentRetrievalService commentRetrievalService;

    public List<TaskDto> findAllTasks(Pageable pageable) {
        return TaskMapping.INSTANCE.tasksToTaskDto(taskRepository.findAll(pageable).getContent());
    }

    public ResponseEntity<?> findFilteredTasks(String title, String status, String priority, String executorEmail,
            String authorEmail, Pageable pageable) {
        TaskStatus taskStatus = null;
        TaskPriority taskPriority = null;

        User author = userService.findByEmail(authorEmail).orElse(null);
        if (authorEmail != null && author == null) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(), "Author not found"),
                    HttpStatus.NOT_FOUND);
        }

        User executor = userService.findByEmail(executorEmail).orElse(null);
        if (executorEmail != null && executor == null) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(), "Executor not found"),
                    HttpStatus.NOT_FOUND);
        }

        if (status != null && !status.isEmpty()) {
            try {
                taskStatus = TaskStatus.valueOf(status);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(
                        new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect status"),
                        HttpStatus.BAD_REQUEST);
            }
        }

        if (priority != null && !priority.isEmpty()) {
            try {
                taskPriority = TaskPriority.valueOf(priority);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(
                        new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect priority"),
                        HttpStatus.BAD_REQUEST);
            }
        }

        List<Task> tasks = taskRepository.findFilteredTasks(title, taskStatus, taskPriority, executor, author, pageable)
                .getContent();
        List<TaskDto> taskDtos = tasks.stream().map(task -> {
            TaskDto taskDto = TaskMapping.INSTANCE.taskToTaskDto(task);
            List<Comment> comments = commentRetrievalService.findAllByTaskId(task.getId());
            taskDto.setComments(comments.stream().map(comment -> {
                CommentDto commentDto = new CommentDto();
                commentDto.setId(comment.getId());
                commentDto.setContent(comment.getContent());
                commentDto.setAuthor(UserMapping.INSTANCE.userToUserDto(comment.getAuthor()));
                return commentDto;
            }).collect(Collectors.toList()));
            return taskDto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(taskDtos);
    }

    public ResponseEntity<?> saveTask(@RequestBody CreateTaskDto task, Principal principal) {
        if (task.getTitle() == null || task.getDescription() == null || task.getTitle().isEmpty()
                || task.getDescription().isEmpty()) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.BAD_REQUEST.value(), "Title and description are required"),
                    HttpStatus.BAD_REQUEST);
        }

        if (task.getTitle().length() > 255 || task.getDescription().length() > 255) {
            return new ResponseEntity<>(
                    new AppError(
                            HttpStatus.BAD_REQUEST.value(),
                            "Title and description cannot be longer than 255 characters"),
                    HttpStatus.BAD_REQUEST);
        }

        Task newTask = TaskMapping.INSTANCE.createTaskDtoToTask(task);
        newTask.setAuthor(userService.findByEmail(principal.getName()).get());
        newTask.setStatus(task.getStatus() == null ? TaskStatus.TODO : task.getStatus());
        newTask.setPriority(task.getPriority() == null ? TaskPriority.LOW : task.getPriority());
        taskRepository.save(newTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(TaskMapping.INSTANCE.taskToTaskDto(newTask));
    }

    @Transactional
    public ResponseEntity<?> deleteTask(@RequestBody Long id, Principal principal) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "Task not found"),
                    HttpStatus.NOT_FOUND);
        }
        if (!taskOptional.get().getAuthor().getEmail().equals(principal.getName())) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.FORBIDDEN.value(), "You are not allowed to delete this task"),
                    HttpStatus.FORBIDDEN);
        }
        commentRetrievalService.deleteAllByTaskId(id);
        taskRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body("Task deleted successfully");
    }

    public ResponseEntity<?> findTask(@RequestBody Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "Task not found"),
                    HttpStatus.NOT_FOUND);
        }
        TaskDto task = TaskMapping.INSTANCE.taskToTaskDto(taskOptional.get());
        List<CommentDto> comments = CommentMapping.INSTANCE.commentsToCommentDto(commentRetrievalService.findAllByTaskId(id));
        task.setComments(comments);

        return ResponseEntity.ok(task);
    }

    public ResponseEntity<?> findTaskByIdWithoutComment(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "Task not found"),
                    HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(taskOptional.get());
    }

    @Transactional
    public ResponseEntity<?> updateTask(@RequestBody Long taskId, UpdateTaskDto updateTaskDto, Principal principal) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "Task not found"),
                    HttpStatus.NOT_FOUND);
        }

        Task task = taskOptional.get();

        if (!isUserAuthorizedToUpdateTask(task, principal)) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.FORBIDDEN.value(), "You are not allowed to update this task"),
                    HttpStatus.FORBIDDEN);
        }

        if (isUserExecutor(task, principal)) {
            return updateTaskStatus(task, updateTaskDto);
        }

        return updateTaskDetails(task, updateTaskDto);
    }

    private boolean isUserAuthorizedToUpdateTask(Task task, Principal principal) {
        String userEmail = principal.getName();
        return task.getAuthor().getEmail().equals(userEmail) || isUserExecutor(task, principal);
    }

    private boolean isUserExecutor(Task task, Principal principal) {
        return task.getExecutor() != null && task.getExecutor().getEmail().equals(principal.getName());
    }

    private ResponseEntity<?> updateTaskStatus(Task task, UpdateTaskDto updateTaskDto) {
        try {
            if (updateTaskDto.getStatus() != null) {
                task.setStatus(TaskStatus.valueOf(updateTaskDto.getStatus()));
            }
            taskRepository.save(task);
            return ResponseEntity.ok(TaskMapping.INSTANCE.taskToTaskDto(task));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid status value"), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<?> updateTaskDetails(Task task, UpdateTaskDto updateTaskDto) {
        TaskMapping.INSTANCE.updateTaskFromDto(updateTaskDto, task);

        if (updateTaskDto.getExecutorEmail() != null) {
            Optional<User> executor = userService.findByEmail(updateTaskDto.getExecutorEmail());
            if (executor.isEmpty()) {
                return new ResponseEntity<>(
                        new AppError(HttpStatus.NOT_FOUND.value(), "Executor not found"), HttpStatus.NOT_FOUND);
            }
            task.setExecutor(executor.get());
        } else {
            User executor = task.getExecutor();
            task.setExecutor(executor.getEmail() == null ? null : executor);
        }

        taskRepository.save(task);
        return ResponseEntity.ok(TaskMapping.INSTANCE.taskToTaskDto(task));
    }
}
