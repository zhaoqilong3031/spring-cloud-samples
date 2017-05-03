package com.zhaoql.support.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.exception.HystrixRuntimeException.FailureType;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityContextRegistratorCommandHook extends HystrixCommandExecutionHook {

    @Override
    public <T> void onRunStart(HystrixCommand<T> commandInstance) {
        log.debug("Hook Thread->{}", Thread.currentThread().getName());
    }

    /**
     * Clean the SecurityContext
     */
    @Override
    public <T> T onComplete(HystrixCommand<T> commandInstance, T response) {
        log.debug("Hook Thread->{}", Thread.currentThread().getName());
        return response;
    }

    /**
     * Clean the SecurityContext
     */
    @Override
    public <T> Exception onError(HystrixCommand<T> commandInstance, FailureType failureType, Exception e) {
        log.debug("Hook Thread->{}", Thread.currentThread().getName());
        SecurityContextHystrixRequestVariable.getInstance().remove();
        return e;
    }


}