package com.zhaoql.order.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhaoql.order.domain.dto.OrderParticipant;
import com.zhaoql.order.domain.entity.Order;
import com.zhaoql.order.provider.client.CoordinatorClient;
import com.zhaoql.order.provider.domain.TccCoordinatorRequest;
import com.zhaoql.order.service.OrderService;

@Component
public class OrderManager {
	@Autowired
	private OrderService orderService;
	

	public void placeOrder(Order order) {
		OrderParticipant participant = orderService.placeOrder(order);
		orderService.confirm(participant);//确认资源
	}
}
