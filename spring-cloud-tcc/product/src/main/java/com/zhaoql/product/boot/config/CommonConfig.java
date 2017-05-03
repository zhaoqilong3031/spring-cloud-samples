
package com.zhaoql.product.boot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.zhaoql.support.web.CustomExceptionHandler;

@Configuration
@ComponentScan(value = { "com.zhaoql.product.web.controller", "com.zhaoql.product.manager",
		"com.zhaoql.product.service" })
@Import(CustomExceptionHandler.class)
public class CommonConfig {

}