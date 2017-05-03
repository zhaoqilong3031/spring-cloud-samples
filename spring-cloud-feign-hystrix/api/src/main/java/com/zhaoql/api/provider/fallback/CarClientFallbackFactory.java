package com.zhaoql.api.provider.fallback;

import com.zhaoql.api.provider.client.TestClient;
import com.zhaoql.core.exception.CustomException;

import feign.hystrix.FallbackFactory;

public class CarClientFallbackFactory implements FallbackFactory<TestClient> {

	@Override
	public TestClient create(Throwable cause) {
		throw new CustomException("回退");
	}

}