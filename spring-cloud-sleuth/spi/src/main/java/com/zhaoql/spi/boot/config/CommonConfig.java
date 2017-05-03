
package com.zhaoql.spi.boot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value={"com.zhaoql.spi.web.controller"})
public class CommonConfig {

}