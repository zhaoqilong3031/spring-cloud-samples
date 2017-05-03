package com.zhaoql.order.service;

import java.util.List;

import javax.transaction.Transactional;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaoql.order.domain.dto.OrderParticipant;
import com.zhaoql.order.domain.entity.Order;
import com.zhaoql.order.provider.client.CoordinatorClient;
import com.zhaoql.order.provider.client.CustomerClient;
import com.zhaoql.order.provider.client.ProductClient;
import com.zhaoql.order.provider.domain.CustomerRequest;
import com.zhaoql.order.provider.domain.ProductRequest;
import com.zhaoql.order.provider.domain.TccCoordinatorRequest;
import com.zhaoql.order.repositories.OrderRepositorie;
import com.zhaoql.support.tcc.Participant;

@Service
public class OrderService {
	@Autowired
	OrderRepositorie orderRepositorie;
	@Autowired
	ProductClient productClient;
	@Autowired
	CustomerClient customerClient;
	@Autowired
	CoordinatorClient coordinatorClient;

	@Transactional
	public OrderParticipant placeOrder(Order order) {
		Order entity = new Order();
		entity.setProdId(order.getProdId());
		entity.setStatus(0);
		entity.setPrice(10);
		entity.setNum(order.getNum());
		entity.setUserId(order.getUserId());
		orderRepositorie.save(entity);
		return new OrderParticipant(entity.getId(),
				com.google.common.collect.Lists.newArrayList(reserveProduct(entity), reserveBalance(entity)));
	}

	/**
	 * 预留商品
	 * 
	 * @param prodId
	 */
	private Participant reserveProduct(Order order) {
		Participant participant = productClient.reserve(new ProductRequest(order.getProdId(), order.getNum()));
		return participant;
	}

	/**
	 * 预留金额
	 * 
	 * @param prodId
	 */
	private Participant reserveBalance(Order order) {
		return customerClient.reserve(new CustomerRequest(order.getUserId(), order.getPrice()));
	}
	
	/**
	 * 确认订单
	 * @param participant
	 */
	public void confirm(OrderParticipant participant) {
		coordinatorClient.confirm(new TccCoordinatorRequest(participant.getParticipants()));
		Order order = orderRepositorie.findOne(participant.getOrderId());
		order.setStatus(1);
		orderRepositorie.save(order);
	}

}
