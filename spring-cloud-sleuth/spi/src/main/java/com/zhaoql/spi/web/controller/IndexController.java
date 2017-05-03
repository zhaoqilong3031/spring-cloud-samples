package com.zhaoql.spi.web.controller;

import java.util.Map;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@RefreshScope
@Slf4j
public class IndexController {

	@RequestMapping(value = { "/index" })
	public Object index(Map<String, Object> model) {
		log.info("服务端首页");
		return "FROM_SPI";
	}

}
