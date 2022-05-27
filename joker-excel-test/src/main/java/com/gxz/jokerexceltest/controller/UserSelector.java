package com.gxz.jokerexceltest.controller;

import org.gxz.joker.starter.component.DynamicReport;
import org.gxz.joker.starter.component.DynamicSelector;
import org.gxz.joker.starter.element.FieldInfo;
import org.gxz.joker.starter.element.IndependentDynamic;
import org.gxz.joker.starter.element.MultiDynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        independentDynamic.setItems(Arrays.asList("张三","李四","王五"));
        dynamicReport.appendIndependentDynamic(independentDynamic);


        MultiDynamic multiDynamic = new MultiDynamic();
        multiDynamic.setParentColIndex(0);
        multiDynamic.setChildColName("邮箱");
        multiDynamic.putItem("服务器",Arrays.asList("服务器1","服务器2","服务器3"));
        multiDynamic.putItem("密码产品",Arrays.asList("密码产品1","密码产品2","密码产品3"));
        dynamicReport.appendMulitLevelDynamic(multiDynamic);

        return dynamicReport;
    }
}
