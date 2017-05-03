package com.zhaoql.order.provider.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class CustomerRequest implements Serializable {
	private Integer id;
	private Integer balance;

	public CustomerRequest(Integer id, Integer balance) {
		super();
		this.id = id;
		this.balance = balance;
	}

	public CustomerRequest() {
		super();
	}

}
