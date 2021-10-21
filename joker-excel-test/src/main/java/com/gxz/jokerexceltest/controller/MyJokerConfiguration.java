package com.gxz.jokerexceltest.controller;

import org.gxz.joker.starter.component.JokerConfiguration;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class MyJokerConfiguration implements JokerConfiguration {
    @Override
    public String getName() {
        return "myJokerConfiguration";
    }

    @Override
    public String excelName() {
        return "这个excel不会有name字段";
    }

    @Override
    public String[] ignore() {
        return new String[]{"name"};
    }
}
