package org.gxz.joker.starter.element;

import org.apache.poi.ss.usermodel.Row;

/**
 * 行描述
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class RowDescription {

    /**
     *
     * 原行
     **/
    private Row row;

    /**
     *
     * 解析成功的数据类型
     **/
    private Class<?> beanType;

    /**
     * 当前行解析成功的数据
     **/
    private Object data;

}
