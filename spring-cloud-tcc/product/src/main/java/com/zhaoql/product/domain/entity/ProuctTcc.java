package com.zhaoql.product.domain.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zhaoql.support.converters.OffsetDateTimePersistenceConverter;

import lombok.Data;
@Data
@Entity
@Table(name = "product_tcc")
public class ProuctTcc implements Serializable {
	@Id
	@GeneratedValue
	private Integer id;
	private Integer entityId;
	private Integer entityType;
	private Integer status;
	@Convert(converter = OffsetDateTimePersistenceConverter.class)
	private OffsetDateTime expire;
	private String snap;
	@Convert(converter = OffsetDateTimePersistenceConverter.class)
	private OffsetDateTime insTm;
	@Convert(converter = OffsetDateTimePersistenceConverter.class)
	private OffsetDateTime updTm;
	
	
}
