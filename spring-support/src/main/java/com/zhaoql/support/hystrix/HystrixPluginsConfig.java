package com.zhaoql.support.hystrix;

import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HystrixPluginsConfig {
//    @Bean
//    public FilterRegistrationBean securityFilterChain(
//            @Qualifier(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME) Filter securityFilter) {
//        FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
//        registration.setOrder(Integer.MAX_VALUE - 2);
//        registration.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
//        return registration;
//    }

    @Bean
    public DelegatingFilterProxyRegistrationBean userInsertingMdcFilterRegistrationBean() {
        DelegatingFilterProxyRegistrationBean registrationBean = new DelegatingFilterProxyRegistrationBean("hystrixRequestContextEnablerFilter", new ServletRegistrationBean[0]);
        registrationBean.setOrder(Integer.MAX_VALUE - 1);
        return registrationBean;
    }

    @Bean
    public DelegatingFilterProxyRegistrationBean setterInsertingMdcFilterRegistrationBean() {
        DelegatingFilterProxyRegistrationBean registrationBean = new DelegatingFilterProxyRegistrationBean("securityContextHystrixRequestVariableSetterFilter", new ServletRegistrationBean[0]);
        registrationBean.setOrder(Integer.MAX_VALUE);
        return registrationBean;
    }
}
