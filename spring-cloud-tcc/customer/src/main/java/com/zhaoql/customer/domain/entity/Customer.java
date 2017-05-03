package com.zhaoql.customer.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@GeneratedValue
	private Integer id;
	private Integer balance;
}
