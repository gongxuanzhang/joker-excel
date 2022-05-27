package org.gxz.joker.starter.element;

import org.springframework.context.ApplicationContext;

/**
 * 组件如果需要Spring Ioc容器 实现接口
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface ApplicationContextInject {
    /**
     * 配置容器
     * @param ioc spring ioc
     **/
    void setApplicationContext(ApplicationContext ioc);


    /**
     * 拿到容器
     *
     * @return spring ioc
     **/
    ApplicationContext getApplicationContext();
}
