package com.zhaoql.api.provider.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhaoql.api.provider.support.FeignClientConfiguration;

@FeignClient(name = "spi", configuration = FeignClientConfiguration.class)
public interface TestClient {

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	String index();
}
