package com.zhaoql.spi.web.controller;

import java.util.Map;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RefreshScope
public class IndexController {

	@RequestMapping(value = { "/index" })
	public Object index(Map<String, Object> model) {

		return "FROM_SPI";
	}

}
