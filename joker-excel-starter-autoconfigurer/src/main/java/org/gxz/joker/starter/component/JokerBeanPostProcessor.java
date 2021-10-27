package org.gxz.joker.starter.component;

import org.gxz.joker.starter.config.JokerConfigurationDelegate;
import org.gxz.joker.starter.config.build.JokerBuilder;
import org.gxz.joker.starter.config.JokerCallBackCombination;
import org.gxz.joker.starter.config.JokerGlobalConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerBeanPostProcessor implements BeanPostProcessor {

    private JokerBuilder jokerBuilder;


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof JokerGlobalConfig) {
            JokerGlobalConfig jokerGlobalConfig = (JokerGlobalConfig) bean;
            jokerGlobalConfig.configure(getJokerBuilder());
            JokerCallBackCombination.registerBuild(getJokerBuilder());
            JokerConfigurationDelegate.registerBuild(getJokerBuilder());
        }
        return bean;
    }


    private JokerBuilder getJokerBuilder() {
        if (jokerBuilder == null) {
            jokerBuilder = new JokerBuilder();
        }
        return jokerBuilder;
    }
}




