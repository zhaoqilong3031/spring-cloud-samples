package com.zhaoql.support.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.zhaoql.support.feign.CustomSecurityContext;

public class SecurityContextHystrixRequestVariable {
    private static final HystrixRequestVariableDefault<CustomSecurityContext> securityContextVariable = new HystrixRequestVariableDefault<>();

    private SecurityContextHystrixRequestVariable() {
    }

    public static HystrixRequestVariableDefault<CustomSecurityContext> getInstance() {
        return securityContextVariable;
    }

}
