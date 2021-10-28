package org.gxz.joker.starter.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelName {

    // TODO: 2021/10/28  这里设置一个更高级的更全面的Excel名称注解


    String value();



}
