package org.gxz.joker.starter.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TextBox {

    String value();

    /**
     * 高度
     **/
    int height() default 6;

    /**
     * 宽度
     **/
    int width() default 10;


}
