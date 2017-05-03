package com.zhaoql.tcc.coordinator.boot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(value = { "com.zhaoql.tcc.coordinator.web.controller" })
public class WebConfig extends WebMvcConfigurerAdapter {

}