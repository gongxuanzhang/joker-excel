package org.gxz.joker.starter.annotation;

import org.gxz.joker.starter.component.DynamicSelector;
import org.gxz.joker.starter.service.DefaultSupport;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加载类上的时候，数据导出之后 会动态生成下拉框及校验内容
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicSelect {


    /**
     * 动态生成的sheet是否隐藏
     **/
    boolean sheetHide() default true;

    /**
     * 生成的sheet名字，如果 {@link #sheetHide()} 是true 将失效
     **/
    String name() default "字典";


    /**
     * 动态选择器的class
     * 如果Spring容器中包含此class 会拿到容器中的内容
     **/
    Class<? extends DynamicSelector> selector();


}
