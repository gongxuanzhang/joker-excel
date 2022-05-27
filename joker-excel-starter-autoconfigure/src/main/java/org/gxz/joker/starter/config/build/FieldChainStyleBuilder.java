package org.gxz.joker.starter.config.build;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface FieldChainStyleBuilder extends StyleBuilder{



    /**
     *
     * 支持链式编程，返回一个定义的字段级别的内容
     * @param fieldName 字段
     * @return new Field
     **/
     FieldStyleBuilder whenField(String fieldName);

}
