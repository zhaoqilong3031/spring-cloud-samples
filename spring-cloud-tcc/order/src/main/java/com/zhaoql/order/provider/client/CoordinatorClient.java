package com.zhaoql.order.provider.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhaoql.order.provider.domain.TccCoordinatorRequest;
import com.zhaoql.order.provider.support.FeignClientConfiguration;
import com.zhaoql.support.tcc.Participant;

@FeignClient(name = "tcc-coordinator", configuration = FeignClientConfiguration.class)
public interface CoordinatorClient {

	@RequestMapping(value = "/coordinator", method = RequestMethod.PUT)
	void confirm(@RequestBody TccCoordinatorRequest request);
	
	@RequestMapping(value = "/coordinator", method = RequestMethod.DELETE)
	void cancel(@RequestBody TccCoordinatorRequest request);
}