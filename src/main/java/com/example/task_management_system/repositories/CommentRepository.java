package com.example.task_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.task_management_system.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTaskId(Long id);
    void deleteAllByTaskId(Long id);
}
