package com.zhaoql.product.service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.zhaoql.core.exception.CustomException;
import com.zhaoql.core.exception.TccCoordinatorException;
import com.zhaoql.product.domain.entity.Product;
import com.zhaoql.product.domain.entity.ProuctTcc;
import com.zhaoql.product.repositories.ProductRepositorie;
import com.zhaoql.product.repositories.ProductTccRepositorie;

@Service
public class ProductService {
	@Autowired
	ProductRepositorie productRepositorie;
	@Autowired
	ProductTccRepositorie productTccRepositorie;
	private final int expireSeconds = 5;
	@Transactional
	public ProuctTcc trying(Product product) {
		Product entity = productRepositorie.findOne(product.getId());
		entity.setNum(entity.getNum() - product.getNum());
		if (entity.getNum() < 0)
			throw new CustomException("数量不足");
		productRepositorie.save(entity);
		ProuctTcc tcc = new ProuctTcc();
		tcc.setEntityId(product.getId());
		tcc.setEntityType(2);
		tcc.setSnap("{\"num\":" + product.getNum() + "}");
		tcc.setExpire(OffsetDateTime.now().plusSeconds(expireSeconds));
		tcc.setInsTm(OffsetDateTime.now());
		tcc.setStatus(0);
		tcc.setUpdTm(OffsetDateTime.now());
		productTccRepositorie.save(tcc);
		return tcc;
	}

	@Transactional
	public void confirm(Integer id) {
		ProuctTcc tcc = productTccRepositorie.findOne(id);
		if (tcc.getStatus() == 1)
			return;
		tcc.setStatus(1);
		productTccRepositorie.save(tcc);
	}

	@Transactional
	public void cancel(Integer id) {
		ProuctTcc tcc = productTccRepositorie.findOne(id);
		if (tcc.getStatus() == 1)
			throw new TccCoordinatorException("事物已完成");
		tcc.setStatus(-1);
		productTccRepositorie.save(tcc);
		//退回库存
		Product entity = productRepositorie.findOne(tcc.getEntityId());
		entity.setNum(entity.getNum() + Integer.parseInt(JSON.parseObject(tcc.getSnap()).get("num").toString()));
		productRepositorie.save(entity);
		
	}

	/**
	 */
	@Scheduled(fixedRate = 10000)
	public void autoCancel() {
		// 获取过期的资源
		final List<ProuctTcc> tccs = productTccRepositorie.expireReservation(2);
		tccs.forEach(tcc -> {
			cancel(tcc.getId());
		});
	}
}
