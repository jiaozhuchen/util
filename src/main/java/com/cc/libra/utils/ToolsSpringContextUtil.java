package com.cc.libra.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.*;

/**
 * Created by chenchen39 on 2018/8/13.
 */
public class ToolsSpringContextUtil implements ApplicationContextAware {



    private static ApplicationContext applicationContext;

    public static void setContext(ApplicationContext applicationContext) {
        ToolsSpringContextUtil.applicationContext = applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setContext(applicationContext);
    }

    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    private static void checkApplicationContext() throws IllegalStateException {
        if(applicationContext == null) {
            throw new IllegalStateException("applicationContext未注入,请在applicationContext.xml中定义SpringContextUtil");
        }
    }

    public static <T> T getBean(String beanName) throws IllegalStateException {
        checkApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }
}
