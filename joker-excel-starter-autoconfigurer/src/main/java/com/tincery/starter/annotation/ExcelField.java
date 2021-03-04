package com.tincery.starter.annotation;

import com.tincery.starter.convert.Converter;
import com.tincery.starter.convert.ConverterRegistry;
import com.tincery.starter.convert.ManagerConverter;
import com.tincery.starter.service.ExcelNameFactory;
import com.tincery.starter.service.NoneNameFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 包含了导出内容的信息
 **/

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {


    /**
     * 名称
     **/
    String name() default "";


    /**
     *  顺序
     **/
    int order() default Integer.MAX_VALUE;

    /***
     * 解析器
     **/
    Class<? extends Converter> converter() default ManagerConverter.class;

    /**
     * cell长度
     **/
    int width() default 4000;

    /****
     * 下拉框内容
     **/
    String[] select() default {};



    /**
     * 如果遇到异常报错 抛出异常消息内容。
     * 支持通配符解析
     * 通配符可选择:
     * r%  解析错误行数 (从1开始计数)
     * c% 解析错误列数
     * v% 解析错误的excel内容
     **/
    String errorMessage() default "";



}
