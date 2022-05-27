package org.gxz.joker.starter.config.build;

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.function.Consumer;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface StyleBuilder {



    /**
     *
     * 支持链式编程，一般来说可以逐级传递
     * 返回一个新的ClassStyleBuilder  看子类具体实现
     * @param beanType class内容
     * @return 返回内容由子类定义意义
     **/
    ClassStyleBuilder whenClass(Class<?> beanType);


    /**
     *
     * 是否允许链式跳转，比如说 还没有调用最终方法，就开始定义另一个，将会出现警告
     * @return true 是正常跳转，false是有未定义的规则
     **/
    boolean chainPermit();



    /**
     *
     * 设置一个style
     * @param cellStyleConsumer 将一个空的cellStyle修改的方法
     *
     **/
    StyleBuilder style(Consumer<CellStyle> cellStyleConsumer);

}
