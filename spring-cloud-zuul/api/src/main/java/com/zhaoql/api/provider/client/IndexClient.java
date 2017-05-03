package com.zhaoql.api.provider.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhaoql.api.provider.fallback.IndexClientFallbackFactory;
import com.zhaoql.api.provider.support.FeignClientConfiguration;

@FeignClient(name = "spi", fallbackFactory = IndexClientFallbackFactory.class, configuration = FeignClientConfiguration.class)
public interface IndexClient {

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	String index();

}
