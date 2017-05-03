package com.zhaoql.support.listenters;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.zhaoql.support.hystrix.SecurityContextRegistratorCommandHook;

import org.slf4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationReadyListenter implements ApplicationListener<ApplicationReadyEvent> {
    Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (event.getApplicationContext().getParent() == null) {
//        	HystrixPlugins.getInstance().registerCommandExecutionHook(impl);
//        	HystrixPlugins.getInstance().getCommandExecutionHook();
//        	if(!(HystrixPlugins.getInstance().getCommandExecutionHook() instanceof SecurityContextRegistratorCommandHook)){
            try {
                HystrixPlugins.getInstance().registerCommandExecutionHook(new SecurityContextRegistratorCommandHook());
            } catch (Exception e) {
                // TODO: handle exception
            }

//        	}

            logger.debug("初始化HystrixPlugins完成");
        }
    }
}