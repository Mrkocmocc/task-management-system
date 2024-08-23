package com.example.task_management_system.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import java.security.Principal;

import com.example.task_management_system.dto.CommentDto;
import com.example.task_management_system.entity.Comment;
import com.example.task_management_system.entity.Task;
import com.example.task_management_system.entity.User;
import com.example.task_management_system.exceptions.AppError;
import com.example.task_management_system.mapping.CommentMapping;
import com.example.task_management_system.repositories.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final UserService userService;

    public List<Comment> findAllByTaskId(Long id) {
        return commentRepository.findAllByTaskId(id);
    }

    public ResponseEntity<?> saveComment(Long taskId, CommentDto commentDto, Principal principal) {
        ResponseEntity<?> responseEntity = taskService.findTaskByIdWithoutComment(taskId);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity;
        }
        if (commentDto.getContent() == null || commentDto.getContent().isEmpty()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Content cannot be empty"),
                    HttpStatus.BAD_REQUEST);
        }
        Task task = (Task) responseEntity.getBody();
        Comment comment = CommentMapping.INSTANCE.commentDtoToComment(commentDto);
        User author = userService.findByEmail(principal.getName()).orElse(null);
        comment.setTask(task);
        comment.setAuthor(author);
        commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommentMapping.INSTANCE.commentToCommentDto(comment));
    }
}
