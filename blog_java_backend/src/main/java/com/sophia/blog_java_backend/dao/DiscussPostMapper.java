package com.sophia.blog_java_backend.dao;

import org.apache.ibatis.annotations.Mapper;
import com.sophia.blog_java_backend.entity.DiscussPost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);
}
