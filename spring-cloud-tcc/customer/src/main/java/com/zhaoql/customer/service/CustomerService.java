package com.zhaoql.customer.service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.zhaoql.core.exception.CustomException;
import com.zhaoql.customer.domain.entity.Customer;
import com.zhaoql.customer.domain.entity.CustomerTcc;
import com.zhaoql.customer.repositories.CustomerRepositorie;
import com.zhaoql.customer.repositories.CustomerTccRepositorie;

@Service
public class CustomerService {
	@Autowired
	CustomerRepositorie customerRepositorie;
	@Autowired
	CustomerTccRepositorie customerTccRepositorie;
	private final int expireSeconds = 5;
	@Transactional
	public CustomerTcc trying(Customer customer) {
		Customer entity = customerRepositorie.findOne(customer.getId());
		entity.setBalance(entity.getBalance() - customer.getBalance());
		if (entity.getBalance() < 0)
			throw new CustomException("余额不足");
		customerRepositorie.save(entity);
		CustomerTcc tcc = new CustomerTcc();
		tcc.setEntityId(customer.getId());
		tcc.setEntityType(1);
		tcc.setSnap("{\"price\":" + customer.getBalance() + "}");
		tcc.setExpire(OffsetDateTime.now().plusSeconds(expireSeconds));
		tcc.setInsTm(OffsetDateTime.now());
		tcc.setStatus(0);
		tcc.setUpdTm(OffsetDateTime.now());
		customerTccRepositorie.save(tcc);
		return tcc;
	}

	@Transactional
	public void confirm(Integer id) {
		CustomerTcc tcc = customerTccRepositorie.findOne(id);
		tcc.setStatus(1);
		customerTccRepositorie.save(tcc);
	}

	@Transactional
	public void cancel(Integer id) {
		CustomerTcc tcc = customerTccRepositorie.findOne(id);
		tcc.setStatus(-1);
		customerTccRepositorie.save(tcc);
		// 退回金额
		Customer entity = customerRepositorie.findOne(tcc.getEntityId());
		entity.setBalance(
				entity.getBalance() + Integer.parseInt(JSON.parseObject(tcc.getSnap()).get("price").toString()));
		customerRepositorie.save(entity);
	}

	/**
	 */
	@Scheduled(fixedRate = 10000)
	public void autoCancel() {
		// 获取过期的资源
		final List<CustomerTcc> tccs = customerTccRepositorie.expireReservation(1);
		tccs.forEach(tcc -> {
			cancel(tcc.getId());
		});
	}
}
