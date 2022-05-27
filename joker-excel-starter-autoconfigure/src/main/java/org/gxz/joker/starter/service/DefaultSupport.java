package org.gxz.joker.starter.service;

import org.gxz.joker.starter.component.DynamicReport;
import org.gxz.joker.starter.component.DynamicSelector;
import org.gxz.joker.starter.component.JokerConfiguration;
import org.gxz.joker.starter.element.FieldInfo;

import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * 这是一个适配器，适配于所有注解Class的默认值
 * 没有任何功能
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public final class DefaultSupport implements ExcelNameFactory, JokerConfiguration, DynamicSelector {

    private DefaultSupport() {

    }


    @Override
    public String getExcelName(Class<?> clazz, Object[] args, Method method) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public DynamicReport dynamic(FieldInfo[] info) {
        return null;
    }
}
