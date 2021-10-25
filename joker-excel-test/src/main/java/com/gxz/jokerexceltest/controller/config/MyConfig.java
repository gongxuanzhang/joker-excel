package com.gxz.jokerexceltest.controller.config;

import org.gxz.joker.starter.component.ExportAspect;
import org.gxz.joker.starter.config.JokerBuilder;
import org.gxz.joker.starter.config.JokerGlobalConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class MyConfig implements JokerGlobalConfig {

    @Autowired
    private ExportAspect jokerAutoConfiguration;

    @Override
    public void configure(JokerBuilder builder) {
        builder.upload()
                .successCallBack((data) -> System.out.println("导入成功"))
                .errorCallBack((row, error) -> System.out.println("失败的行"))
                .finishCallBack((data) -> System.out.println("完成的行数"));

    }
}
