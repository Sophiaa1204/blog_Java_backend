package com.sophia.blog_java_backend;

import com.sophia.blog_java_backend.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
@ContextConfiguration(classes = BlogJavaBackendApplication.class)
public class MailTests {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail() {
        mailClient.sendMail("sw572@duke.edu","test","Welcome!");
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username","Sophia");

        String content = templateEngine.process(("/mail/demo"), context);
        System.out.println(content);
        mailClient.sendMail("sw572@duke.edu","HTML",content);
    }
}
