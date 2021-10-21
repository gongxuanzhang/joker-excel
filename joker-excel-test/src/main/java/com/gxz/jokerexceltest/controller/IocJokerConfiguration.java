package com.gxz.jokerexceltest.controller;

import org.gxz.joker.starter.component.JokerConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/

@Component
public class IocJokerConfiguration implements JokerConfiguration, ApplicationContextAware {

    String excelName;


    @Override
    public String getName() {
        return "iocConfiguration";
    }

    @Override
    public String excelName() {
        return excelName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        excelName = "这说明你的Ioc生效了";
    }
}
