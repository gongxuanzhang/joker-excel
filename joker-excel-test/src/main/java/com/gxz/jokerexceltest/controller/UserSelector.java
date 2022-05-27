package com.gxz.jokerexceltest.controller;

import org.gxz.joker.starter.component.DynamicReport;
import org.gxz.joker.starter.component.DynamicSelector;
import org.gxz.joker.starter.element.FieldInfo;
import org.gxz.joker.starter.element.IndependentDynamic;
import org.gxz.joker.starter.element.MultiDynamic;

import java.util.Arrays;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class UserSelector implements DynamicSelector {
    @Override
    public DynamicReport dynamic(FieldInfo[] info) {
        DynamicReport dynamicReport = new DynamicReport();
        IndependentDynamic independentDynamic = new IndependentDynamic();
        independentDynamic.setColName("姓名");
        independentDynamic.setItems(Arrays.asList("张三","张三0"));
        dynamicReport.appendIndependentDynamic(independentDynamic);

        IndependentDynamic independentDynamic1 = new IndependentDynamic();
        independentDynamic1.setIndex(4);
        independentDynamic1.setItems(Arrays.asList("张三","张三0"));
        dynamicReport.appendIndependentDynamic(independentDynamic1);

        MultiDynamic multiDynamic = new MultiDynamic();
        multiDynamic.setParentColIndex(0);
        multiDynamic.setChildColIndex(3);
        multiDynamic.putItem("敏捷英雄",Arrays.asList("火枪","影魔","傻逼"));
        dynamicReport.appendMulitLevelDynamic(multiDynamic);

        return dynamicReport;
    }
}
