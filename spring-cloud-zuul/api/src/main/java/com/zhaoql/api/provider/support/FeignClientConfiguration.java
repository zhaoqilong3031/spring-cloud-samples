package com.zhaoql.api.provider.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhaoql.api.provider.fallback.IndexClientFallbackFactory;

@Configuration
public class FeignClientConfiguration {
	@Bean
	public IndexClientFallbackFactory indexClientFallbackFactory() {
		return new IndexClientFallbackFactory();
	}

}