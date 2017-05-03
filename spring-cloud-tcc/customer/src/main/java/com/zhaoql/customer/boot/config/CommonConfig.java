
package com.zhaoql.customer.boot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value={"com.zhaoql.customer.web.controller","com.zhaoql.customer.manager","com.zhaoql.customer.service"})
public class CommonConfig {

}