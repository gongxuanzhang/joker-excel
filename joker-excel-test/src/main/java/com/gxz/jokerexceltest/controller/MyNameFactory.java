package com.gxz.jokerexceltest.controller;

import org.gxz.joker.starter.service.ExcelNameFactory;

import java.lang.reflect.Method;

/**
 * 导出一个带时间戳的excel
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class MyNameFactory implements ExcelNameFactory {
    @Override
    public String getExcelName(Class<?> clazz, Object[] args, Method method) {
        return "工厂名称" + System.currentTimeMillis();
    }
}
