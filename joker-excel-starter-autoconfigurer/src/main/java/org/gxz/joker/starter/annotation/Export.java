package org.gxz.joker.starter.annotation;

import org.gxz.joker.starter.component.JokerConfiguration;
import org.gxz.joker.starter.element.DefaultValueConstant;
import org.gxz.joker.starter.service.DefaultSupport;
import org.gxz.joker.starter.service.ExcelNameFactory;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用在方法上  声明此方法是导出内容
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Export {


    /**
     * 导出的excel的名字，如果配置了nameFactory() 那此属性将无效
     **/
    String value() default DefaultValueConstant.EXPORT_EXCEL_NAME;

    /**
     * 创建的第一个Sheet的名字，默认不支持多Sheet
     **/
    String sheetName() default DefaultValueConstant.SHEET_EXCEL_NAME;

    /**
     * Export的方法注解，优先级高于ExcelData的类注解
     * ignore() 和include() 两个组合判断 分别分析
     * 1. 两个都是默认值------> 如果 allField() == true  将导出所有属性
     * 如果 allField() == false  将只导出拥有ExcelField属性的字段
     * <p>
     * 2. ignore()不是默认值   include()是默认值----->
     * 如果 allField() == true  会把从所有属性中忽略 ignore 同名字段
     * 如果 allField() == false  将只导出拥有ExcelField属性的字段，同时会忽略ignore同名字段
     * 3. ignore()是默认值   include()不是默认值----->
     * 此时直接忽略allField() 属性值，  只会导出include中同名字段
     * 4. ignore() include()两个都不是默认值----->
     * 此时会默认ignore为默认值 请参考 第3种情况
     **/
    String[] ignore() default {};

    String[] include() default {};

    boolean allField() default DefaultValueConstant.ALL_FIELD;

    /**
     * 指定导出excel的命名工厂
     * 此工厂也可以是spring容器中的类
     **/
    Class<? extends ExcelNameFactory> nameFactory() default DefaultSupport.class;

    /**
     * 定义一个当前导出的配置类，如果此方法返回的不是默认值，
     * 那么此方法的优先级会大于其他属性的优先级。(可以通过)
     * todo 暂未实现
     **/
    Class<? extends JokerConfiguration> configurationClass() default DefaultSupport.class;

}
