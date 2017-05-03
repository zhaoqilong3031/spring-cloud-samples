package com.zhaoql.api.provider.domain;

import lombok.Data;

@Data
public class ParamVo {
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ParamVo(String id) {
		super();
		this.id = id;
	}

}
