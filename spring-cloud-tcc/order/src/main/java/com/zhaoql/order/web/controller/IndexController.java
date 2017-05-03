package com.zhaoql.order.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhaoql.order.domain.entity.Order;
import com.zhaoql.order.manager.OrderManager;

@RestController
@RequestMapping
@RefreshScope
public class IndexController {
	@Autowired
	OrderManager orderManager;

	@RequestMapping(value = { "/place-order" })
	public String placeOrder(@RequestBody Order request) {
		orderManager.placeOrder(request);
		return "成功";
	}

}
