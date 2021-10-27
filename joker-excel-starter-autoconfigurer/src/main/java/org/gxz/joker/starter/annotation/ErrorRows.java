package org.gxz.joker.starter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 导入导出时，如果出现了问题
 * 可以把此注解加载Spring方法中的错误内容上 直接进行操作
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorRows {
}
