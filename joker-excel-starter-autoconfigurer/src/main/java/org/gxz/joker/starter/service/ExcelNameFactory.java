package org.gxz.joker.starter.service;


import java.lang.reflect.Method;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * excel导出时的命名工厂，
 * 命名工厂如果不在spring容器中，将会被反射实例化，存储在缓存中
 * 缓存不是持久化的，默认只存储1小时，如果命名工厂有全局逻辑，可以设置singleton方法为true让实例永久存储
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
    String getExcelName(Class<?> clazz, Object[] args, Method method);

    /**
     * 此工厂被创建之后是否永久单例
     * @return true为永久单例，默认为非单例(可以被回收，不持久化数据)
     */
    default boolean singleton() {
        return false;
    }


}
