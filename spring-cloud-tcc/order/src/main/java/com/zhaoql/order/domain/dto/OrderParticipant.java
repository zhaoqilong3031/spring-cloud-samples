package com.zhaoql.order.domain.dto;

import java.util.List;

import com.zhaoql.support.tcc.Participant;

import lombok.Data;

@Data
public class OrderParticipant {
	private Integer orderId;
	private List<Participant> participants;

	public OrderParticipant(Integer orderId, List<Participant> participants) {
		super();
		this.orderId = orderId;
		this.participants = participants;
	}

}
