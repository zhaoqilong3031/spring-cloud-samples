package com.zhaoql.support.feign;

import java.io.IOException;
import java.lang.reflect.Constructor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.zhaoql.core.domain.ErrorResult;
import com.zhaoql.core.exception.CustomException;
import com.zhaoql.support.hystrix.SecurityContextHystrixRequestVariable;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常拦截
 *
 * @author Administrator
 */
@Slf4j
public class DefaultFeignExceptionHandlerInterceptor {


    /*
     * 定义一个切入点
     */
    @Pointcut("execution(* com.cmy.*.provider.client..*.*(..))")
    public void point() {

    }

    /**
     * @param data
     * @throws ClassNotFoundException
     * @throws HttpMessageNotWritableException
     * @throws IOException
     */
    @AfterReturning(pointcut = "point()", returning = "data")
    public void after(JoinPoint jp, Object data) throws Exception {
        MethodInvocationProceedingJoinPoint joinPoint = (MethodInvocationProceedingJoinPoint) jp;
        Class<?> clazz = joinPoint.getSignature().getDeclaringType();
        if (AnnotationUtils.findAnnotation(clazz, FeignClient.class) == null) {
            log.debug("未找到feign 客户端");
            return;
        }
        CustomSecurityContext securityContext = SecurityContextHystrixRequestVariable.getInstance().get();
        ErrorResult errorResult = null;
        if (securityContext != null && (errorResult = securityContext.getErrorResult()) != null) {
            if (errorResult.getHttpStatus() != HttpStatus.OK.value()) {
                Class<?> exceptionClass = Class.forName(errorResult.getException());
                Constructor<?> constructor = exceptionClass.getConstructor(new Class[]{String.class, String.class});
                throw (CustomException) constructor
                        .newInstance(new Object[]{errorResult.getMessage(), errorResult.getCode()});
            }
        }
        SecurityContextHystrixRequestVariable.getInstance().remove();
    }
}