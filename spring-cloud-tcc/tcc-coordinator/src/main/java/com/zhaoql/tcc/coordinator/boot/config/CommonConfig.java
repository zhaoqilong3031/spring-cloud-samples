
package com.zhaoql.tcc.coordinator.boot.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

import com.zhaoql.support.web.CustomExceptionHandler;

@EnableEurekaClient
@EnableHystrix
@EnableRetry
@Configuration
@ComponentScan(basePackages = { "com.zhaoql.tcc.coordinator.manager" })
@Import(value = { CustomExceptionHandler.class })
public class CommonConfig {

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}