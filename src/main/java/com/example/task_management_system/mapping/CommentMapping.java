package com.example.task_management_system.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.task_management_system.dto.CommentDto;
import com.example.task_management_system.entity.Comment;

import java.util.List;

@Mapper
public interface CommentMapping {
    CommentMapping INSTANCE = Mappers.getMapper(CommentMapping.class);

    CommentDto commentToCommentDto(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "author", ignore = true)
    Comment commentDtoToComment(CommentDto commentDto);

    List<CommentDto> commentsToCommentDto(List<Comment> comment);

}
