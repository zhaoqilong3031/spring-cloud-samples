package com.zhaoql.order.provider.support;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import com.zhaoql.support.feign.DefaultFeignExceptionHandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Configuration
@Slf4j
public class FeignExceptionHandler extends DefaultFeignExceptionHandlerInterceptor {
	 
	/*
	 * 定义一个切入点
	 */
	@Pointcut("execution(* com.zhaoql.*.provider.client..*.*(..))")
	public void point() {

	}

	 
}
