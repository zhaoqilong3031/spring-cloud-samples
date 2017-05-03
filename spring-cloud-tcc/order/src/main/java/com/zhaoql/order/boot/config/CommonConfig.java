
package com.zhaoql.order.boot.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.zhaoql.support.web.CustomExceptionHandler;

@Configuration
@ComponentScan(value={"com.zhaoql.order.web.controller","com.zhaoql.order.manager","com.zhaoql.order.service"})
@EnableFeignClients(basePackages={"com.zhaoql.order.provider.client"})
@Import(CustomExceptionHandler.class)
public class CommonConfig {

}