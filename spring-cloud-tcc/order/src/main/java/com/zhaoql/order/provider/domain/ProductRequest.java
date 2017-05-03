package com.zhaoql.order.provider.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductRequest implements Serializable {
	private Integer id;
	private Integer num;

	public ProductRequest(Integer id, Integer num) {
		super();
		this.id = id;
		this.num = num;
	}

	public ProductRequest() {
		super();
	}

}
