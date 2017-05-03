package com.zhaoql.order.boot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.zhaoql.support.jpa.RepositoryFactoryBean;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "commonEntityManagerFactory", transactionManagerRef = "commonTransactionManager",
basePackages = { "com.zhaoql.order.repositories" } , repositoryFactoryBeanClass = RepositoryFactoryBean.class)
@EnableTransactionManagement
public class JpaCommonConfig {
	@Autowired
	@Qualifier("commonDataSource")
	DataSource dataSource;
	@Autowired
	private JpaProperties jpaProperties;
 
	@Bean(name = "commonEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource).properties(jpaProperties.getHibernateProperties(dataSource)).packages("com.zhaoql.order.domain.entity").build();
	}

	@Bean(name = "commonTransactionManager")
	public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
		return txManager;
	}
 
}
