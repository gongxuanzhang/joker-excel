package org.gxz.joker.starter.config;

import org.gxz.joker.starter.config.build.JokerBuilder;

/**
 * 全局配置
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/

public interface JokerGlobalConfig {

    /**
     * 全局配置
     *
     * @param builder 配置内容建造者
     **/
    void configure(JokerBuilder builder);
}
