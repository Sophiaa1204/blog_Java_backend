package com.sophia.blog_java_backend.service;

import com.sophia.blog_java_backend.dao.AlphaDao;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class AlphaService {
    @Autowired
    private AlphaDao alphaDao;

    public AlphaService() {
        System.out.println("In Constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("Initialization");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroy Alpha Service");
    }

    public String find() {
        return alphaDao.select();
    }
}
