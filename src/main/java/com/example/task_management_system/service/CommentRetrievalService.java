package com.example.task_management_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.task_management_system.entity.Comment;
import com.example.task_management_system.repositories.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentRetrievalService {
    private final CommentRepository commentRepository;

    public List<Comment> findAllByTaskId(Long taskId) {
        return commentRepository.findAllByTaskId(taskId);
    }

    public void deleteAllByTaskId(Long taskId) {
        commentRepository.deleteAllByTaskId(taskId);
    }
}
