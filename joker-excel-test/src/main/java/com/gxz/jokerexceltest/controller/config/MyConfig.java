package com.gxz.jokerexceltest.controller.config;

import com.gxz.jokerexceltest.controller.User;
import com.gxz.jokerexceltest.controller.expression.MyExpressionParser;
import org.gxz.joker.starter.config.JokerGlobalConfig;
import org.gxz.joker.starter.config.build.JokerBuilder;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class MyConfig implements JokerGlobalConfig {

    @Override
    public void configure(JokerBuilder builder) {
        builder.expression().addParser(new MyExpressionParser());
                // 所有表头都填充24颜色
                //  直接通过head .style()
                        builder.head().style((style) -> style.setFillBackgroundColor((short) 24));
    }

    public static void main(String[] args) {
        JokerBuilder jokerBuilder = new JokerBuilder();
        jokerBuilder.head().whenClass(User.class).style((s)->s.setDataFormat((short)1))
                .whenField("name");
    }
}
