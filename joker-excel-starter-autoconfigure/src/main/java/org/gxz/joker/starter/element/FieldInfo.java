package org.gxz.joker.starter.element;

import lombok.Data;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
public class FieldInfo {

    /**
     * 最终暴露在excel中的列名
     **/
    private String exportColumnName;

    /**
     * 此列在对象中的原始属性名
     **/
    private String rawFieldName;


}
