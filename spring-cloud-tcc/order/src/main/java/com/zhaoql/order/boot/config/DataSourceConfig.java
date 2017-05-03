package com.zhaoql.order.boot.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DataSourceConfig {

	@Bean("commonDruidDataProperties")
	@ConfigurationProperties(prefix = "spring.datasource.common")
	public Map<String, String> beautyDruidDataProperties() {
		return new HashMap<String, String>();
	}

	@Bean(name = "commonDataSource")
	@Primary
	public DataSource druidDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		MutablePropertyValues properties = new MutablePropertyValues(beautyDruidDataProperties());
		new RelaxedDataBinder(dataSource).bind(properties);
		return dataSource;
	}
}
