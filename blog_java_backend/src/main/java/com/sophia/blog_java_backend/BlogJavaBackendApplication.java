package com.sophia.blog_java_backend;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogJavaBackendApplication {

	@PostConstruct
	public void init() {
		// 解决netty启动冲突问题
		// setAvailableProcessors
		System.setProperty("es.set.netty.runtime.available.processors", "false");
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogJavaBackendApplication.class, args);
	}

}
