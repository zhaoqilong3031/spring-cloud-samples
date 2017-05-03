package com.zhaoql.order.provider.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhaoql.order.provider.domain.ProductRequest;
import com.zhaoql.order.provider.support.FeignClientConfiguration;
import com.zhaoql.support.tcc.Participant;

@FeignClient(name = "product", configuration = FeignClientConfiguration.class)
public interface ProductClient {

	@RequestMapping(value = "/trying", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
	Participant reserve(@RequestBody ProductRequest request);
}
