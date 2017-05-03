package com.zhaoql.api.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoql.api.provider.client.IndexClient;

@RestController
@RequestMapping
@RefreshScope
public class IndexController {
	@Autowired
	IndexClient indexClient;

	@RequestMapping(value = { "/index" })
	public String index(Map<String, Object> model) {

		return indexClient.index();
	}

}
