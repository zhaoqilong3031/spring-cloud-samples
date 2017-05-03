package com.zhaoql.order.provider.support;

import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.zhaoql.support.feign.DefaultFeignClientConfiguration;


@Configuration
@Import(FeignClientsConfiguration.class)
public class FeignClientConfiguration extends DefaultFeignClientConfiguration {


}