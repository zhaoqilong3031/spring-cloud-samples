package com.zhaoql.gateway.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@EnableZuulProxy
@EnableEurekaClient
@ComponentScan(value = { "com.zhaoql.gateway.controller"})
@SpringBootApplication(exclude = { WebSocketAutoConfiguration.class, SessionAutoConfiguration.class,
		SendGridAutoConfiguration.class, FreeMarkerAutoConfiguration.class })
public class GatewayApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GatewayApplication.class, args);
	}
	
	
//	@Bean
//	public PatternServiceRouteMapper serviceRouteMapper() {
//	    return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)","${version}/${name}");
//	}


}
