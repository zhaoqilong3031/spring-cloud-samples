package com.zhaoql.gateway.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorHandelController implements ErrorController {


	@Value("${error.path:/error}")
	private String errorPath;

	@RequestMapping(value = "/error")
	public String handle(HttpServletRequest request) {
		return "网关秀逗了！";
	}

	@Override
	public String getErrorPath() {
		return this.errorPath;
	}
}
