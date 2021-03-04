package com.tincery.starter.service;

import java.lang.reflect.Method;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 这是个默认的工厂 这个工厂不允许创建 而且永远不会有实例出现
 **/
public final class NoneNameFactory implements ExcelNameFactory {

    private NoneNameFactory() {

    }


    @Override
    public String getExcelName(Class<?> clazz, Object[] args, Method method) {
        return null;
    }
}
