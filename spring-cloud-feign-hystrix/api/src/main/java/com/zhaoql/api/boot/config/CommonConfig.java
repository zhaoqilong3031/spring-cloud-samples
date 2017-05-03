
package com.zhaoql.api.boot.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.zhaoql.support.web.CustomExceptionHandler;

@Configuration
@ComponentScan(basePackages = {  "com.zhaoql.support.hystrix","com.zhaoql.api.provider.support" })
@EnableFeignClients(basePackages = "com.zhaoql.api.provider.client")
@Import(value = {CustomExceptionHandler.class})
public class CommonConfig {

}