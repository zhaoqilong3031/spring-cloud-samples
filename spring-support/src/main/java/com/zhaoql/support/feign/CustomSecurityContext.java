package com.zhaoql.support.feign;

import com.zhaoql.core.domain.ErrorResult;

import lombok.Data;

@Data
public class CustomSecurityContext {
	public ErrorResult errorResult;

	public static CustomSecurityContext getInterface() {
		CustomSecurityContext cmySecurityContext = new CustomSecurityContext();
		return cmySecurityContext;
	}
}