package com.example.task_management_system.mapping;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

import com.example.task_management_system.dto.CreateTaskDto;
import com.example.task_management_system.dto.TaskDto;
import com.example.task_management_system.dto.UpdateTaskDto;
import com.example.task_management_system.entity.Task;

@Mapper
public interface TaskMapping {

    TaskMapping INSTANCE = Mappers.getMapper(TaskMapping.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "executor", ignore = true)
    Task createTaskDtoToTask(CreateTaskDto createTaskDto);

    @Mapping(source = "author.email", target = "authorEmail")
    @Mapping(source = "executor.email", target = "executorEmail")
    @Mapping(target = "comments", ignore = true)
    TaskDto taskToTaskDto(Task task);

    @Mapping(source = "authorEmail", target = "author.email")
    @Mapping(source = "executorEmail", target = "executor.email")
    Task taskDtoToTask(TaskDto taskDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(source = "executorEmail", target = "executor.email")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskFromDto(UpdateTaskDto updateTaskDto, @MappingTarget Task task);

    List<TaskDto> tasksToTaskDto(List<Task> task);
}
