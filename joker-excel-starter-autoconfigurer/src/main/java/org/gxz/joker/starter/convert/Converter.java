package org.gxz.joker.starter.convert;


import org.gxz.joker.starter.exception.ConvertException;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 从类型转换成excel中的内容
 **/
public interface Converter<T> {

    /**
     * 把实体字段映射成一个单元格的内容
     * 导入时使用
     *
     * @param value 实体的当前属性值
     * @return 单元格的内容
     **/
    String convert(T value) throws ConvertException;

    /**
     * 导出时使用
     * @param cellValue 单元格内容
     * @param clazz     需要映射的类型
     * @return 一个单元格映射成一个字段 或者一个实体
     **/
    T reconvert(String cellValue, Class<? extends T> clazz) throws ConvertException;

}
