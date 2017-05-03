package com.zhaoql.support.feign;

import static feign.FeignException.errorStatus;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.zhaoql.core.exception.CustomException;
import com.zhaoql.core.utils.HttpErrorDecoder;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;

public abstract class DefaultFeignClientConfiguration {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ObjectFactory<HttpMessageConverters> messageConverters;

	@Bean
	public ErrorDecoder buildErrorDecoder() {
		return new ErrorDecoder() {
			@Override
			public Exception decode(String methodKey, Response response) {
				logger.error("feign失败  方法 ：{},原因：{}", methodKey, response);
				try {
					if (response.body() != null) {
						CustomException exception = HttpErrorDecoder.decode(HttpStatus.valueOf(response.status()),
								Util.toString(response.body().asReader()));
						return new HystrixBadRequestException(exception.getMessage(), exception);
					}
				} catch (IOException e) {

				}
				return errorStatus(methodKey, response);
			}
		};
	}

	@Bean
	public feign.codec.Encoder CustomEncode() {
		return new CustomEncode(messageConverters);
	}

}