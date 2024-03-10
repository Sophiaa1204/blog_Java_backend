package com.sophia.blog_java_backend.controller.advice;

import com.sophia.blog_java_backend.util.CommunityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.io.PrintWriter;

// 只扫描Controller的组件
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);
    // 所有的异常都由其处理
    @ExceptionHandler({Exception.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器发生异常： " + e.getMessage());
        // 遍历栈的信息
        for(StackTraceElement element: e.getStackTrace()) {
            logger.error(element.toString());
        }

        // JSON还是页面？
        String xRequestedWith = request.getHeader("x-requested-with");
        if ("XMLHttpRequest".equals(xRequestedWith)) {
            // 会自动转换成JSON，如果是plain需要手动转
            response.setContentType("application/plain; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJSONString(1,"服务器异常！"));
        } else {
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }
}
