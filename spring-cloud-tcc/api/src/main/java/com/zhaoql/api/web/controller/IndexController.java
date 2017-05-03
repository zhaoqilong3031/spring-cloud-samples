package com.zhaoql.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoql.api.provider.client.TestClient;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@RefreshScope
@Slf4j
public class IndexController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	TestClient client;

	@RequestMapping(value = { "/index" })
	public String env() {
		return client.index();
	}

}
