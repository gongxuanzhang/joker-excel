package com.gxz.jokerexceltest.controller.config;

import com.gxz.jokerexceltest.controller.IocJokerConfiguration;
import com.gxz.jokerexceltest.controller.expression.MyExpressionParser;
import org.gxz.joker.starter.component.ExportAspect;
import org.gxz.joker.starter.config.build.JokerBuilder;
import org.gxz.joker.starter.config.JokerGlobalConfig;
import org.gxz.joker.starter.expression.AutoJokerExpressionParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class MyConfig implements JokerGlobalConfig {

    @Override
    public void configure(JokerBuilder builder) {
        builder.expression().addParser(new MyExpressionParser());
    }
}
