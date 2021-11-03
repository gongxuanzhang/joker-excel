package org.gxz.joker.starter.service;

import lombok.Data;
import org.gxz.joker.starter.config.ExcelFieldDescription;
import org.gxz.joker.starter.convert.Converter;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
public class DataRule {

    /**
     * 数据中 字段名称
     **/
    private String fieldName;

    /**
     * 数据字段类型
     **/
    private Class<?> filedType;

    /**
     * 转换器
     **/
    private Converter<?> converter;

    /**
     * 字段描述
     **/
    private ExcelFieldDescription excelFieldDescription;

    /**
     *
     * 设置样式的内容, todo 这里需要有过滤器和样式内容设置
     **/


}
