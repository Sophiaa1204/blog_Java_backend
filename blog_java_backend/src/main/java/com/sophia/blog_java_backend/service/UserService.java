package com.sophia.blog_java_backend.service;

import com.sophia.blog_java_backend.dao.UserMapper;
import com.sophia.blog_java_backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id) {
        return userMapper.selectById(id);
    }
}
