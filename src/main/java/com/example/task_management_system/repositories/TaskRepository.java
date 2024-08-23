package com.example.task_management_system.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.task_management_system.entity.Task;
import com.example.task_management_system.entity.TaskPriority;
import com.example.task_management_system.entity.TaskStatus;
import com.example.task_management_system.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
        Page<Task> findAll(Pageable pageable);

        @Query("SELECT t FROM Task t WHERE " +
                        "(:title IS NULL OR t.title LIKE %:title%) AND " +
                        "(:status IS NULL OR t.status = :status) AND " +
                        "(:priority IS NULL OR t.priority = :priority) AND " +
                        "(:executor IS NULL OR t.executor = :executor) AND " +
                        "(:author IS NULL OR t.author = :author)")
        Page<Task> findFilteredTasks(@Param("title") String title,
                        @Param("status") TaskStatus status, @Param("priority") TaskPriority priority,
                        @Param("executor") User executor, @Param("author") User author,
                        Pageable pageable);

}
