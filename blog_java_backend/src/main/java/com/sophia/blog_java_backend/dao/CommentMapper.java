package com.sophia.blog_java_backend.dao;

import com.sophia.blog_java_backend.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);
    int selectCountByEntity(int entityType, int entityId);

    int insertComment(Comment comment);

}
