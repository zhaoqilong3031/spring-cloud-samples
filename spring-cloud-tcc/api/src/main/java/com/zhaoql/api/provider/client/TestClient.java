package com.zhaoql.api.provider.client;

import java.io.File;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhaoql.api.provider.domain.ParamVo;
import com.zhaoql.api.provider.fallback.CarClientFallbackFactory;
import com.zhaoql.api.provider.support.FeignClientConfiguration;

@FeignClient(name = "test-service", configuration = FeignClientConfiguration.class)
public interface TestClient {

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	String index();

	@RequestMapping(value = "/index1", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	String index1(@RequestHeader("Content-Type22") final String token, String param);

	@RequestMapping(value = "/index2", method = RequestMethod.POST)
	String index2(@RequestBody ParamVo vo);

	@RequestMapping(value = "/index3", method = RequestMethod.POST)
	String index3(byte[] file);
}
