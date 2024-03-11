//package com.sophia.blog_java_backend.aspect;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.stereotype.Component;
//
//@Component
//@Aspect
//public class AlphaAspect {
//    @Pointcut("execution(* com.sophia.blog_java_backend.service.*.*(..))")
//    // 所有的业务组件的所有的方法的（..)->所有的参数，所有的返回值
//    public void pointcut() {
//
//    }
//
//    @Before("pointcut()")
//    public void before(){
//        System.out.println("before");
//    }
//
//    @After("pointcut()")
//    public void after(){
//        System.out.println("after");
//    }
//
//    @AfterReturning("pointcut()")
//    public void afterReturning(){
//        System.out.println("afterReturning");
//    }
//
//    @AfterThrowing("pointcut()")
//    public void afterThrowing(){
//        System.out.println("afterThrowing");
//    }
//
//    @Around("pointcut()")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("around before");
//        Object obj = joinPoint.proceed();
//        System.out.println("around after");
//        return obj;
//    }
//}
