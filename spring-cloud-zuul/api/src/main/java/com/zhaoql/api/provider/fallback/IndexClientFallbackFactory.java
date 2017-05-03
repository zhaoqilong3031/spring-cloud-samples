package com.zhaoql.api.provider.fallback;

import com.zhaoql.api.provider.client.IndexClient;

import feign.hystrix.FallbackFactory;

public class IndexClientFallbackFactory implements FallbackFactory<IndexClient> {

	@Override
	public IndexClient create(Throwable cause) {
		return new IndexClient() {
			
			@Override
			public String index() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}