package com.zhaoql.order.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "`order`")
public class Order {
	@Id
	@GeneratedValue
	private Integer id;
	private Integer prodId;
	private Integer userId;
	private Integer price;
	private Integer status;
	private Integer num;
}
