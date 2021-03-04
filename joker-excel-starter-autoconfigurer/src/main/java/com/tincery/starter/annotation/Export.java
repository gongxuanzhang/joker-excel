package com.tincery.starter.annotation;

import com.tincery.starter.service.ExcelNameFactory;
import com.tincery.starter.service.NoneNameFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 用在方法上  声明此方法是导出内容
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Export {

    String value() default "excel.xlsx";


    String sheetName() default "sheet";

    Class<? extends ExcelNameFactory> nameFactory() default NoneNameFactory.class;

}
