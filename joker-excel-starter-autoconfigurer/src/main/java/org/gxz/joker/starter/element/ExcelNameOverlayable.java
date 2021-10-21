package org.gxz.joker.starter.element;

import org.gxz.joker.starter.service.ExcelNameFactory;

import java.util.Objects;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface ExcelNameOverlayable extends StringOverlayable {


    /**
     * 提供默认的值
     **/
    @Override
    default String defaultValue() {
        return DefaultValueConstant.EXPORT_EXCEL_NAME;
    }

    /**
     *
     * 判断传入的工厂类是否是默认值
     **/
    default boolean factoryDefault(Class<? extends ExcelNameFactory> factoryClass) {
        return Objects.equals(factoryClass, DefaultValueConstant.DEFAULT_SUPPORT_CLASS);
    }


}
