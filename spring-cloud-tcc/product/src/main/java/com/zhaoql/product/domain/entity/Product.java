package com.zhaoql.product.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
@Entity
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue
	private Integer id;
	private Integer price;
	private Integer num;
}
