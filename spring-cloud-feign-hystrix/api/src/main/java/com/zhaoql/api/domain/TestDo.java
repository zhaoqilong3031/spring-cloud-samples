package com.zhaoql.api.domain;

import java.util.List;

import lombok.Data;

@Data
public class TestDo {
	private String a;
	private List<TestDo> list;

	public TestDo() {

	}
	public TestDo(String a) {
		this.a = a;
	}
	public TestDo(String a, List<TestDo> list) {
		this.a = a;
		this.list = list;
	}
}
