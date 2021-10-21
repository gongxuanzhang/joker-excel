package org.gxz.joker.starter.service;


import java.lang.reflect.Method;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * excel导出时的命名工厂
 **/
public interface ExcelNameFactory {


    /**
     * 生成的命名规则
     *
     * @param clazz  这是导出的实体的Class
     * @param args   调用方法的参数内容
     * @param method 调用的反射方法内容
     * @return 返回最终生成的excel导出名称
     **/
    public String getExcelName(Class<?> clazz, Object[] args, Method method);

}
