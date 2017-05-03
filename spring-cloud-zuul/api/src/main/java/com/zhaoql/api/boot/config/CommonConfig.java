
package com.zhaoql.api.boot.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.zhaoql.api.web.controller"})
@EnableFeignClients(basePackages = "com.zhaoql.api.provider.client")
public class CommonConfig {

}