package com.tincery.starter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 加入此注解声明此实体是一个可以自动导出的实体
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelData {


    String[] ignore() default {};

    String[] include() default {};


}
