package org.gxz.joker.starter.config;

import lombok.Getter;
import org.gxz.joker.starter.annotation.ExcelField;

/**
 * 字段描述
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Getter
public class ExcelFieldDescription {


    public ExcelFieldDescription(ExcelField excelField) {
        this.isRequire = excelField.require();
        this.isUnique = excelField.unique();
        this.select = excelField.select();
    }


    /**
     * 字段是否必须填写
     **/
    private final boolean isRequire;

    /**
     * 字段是否 唯一
     **/
    private final boolean isUnique;

    /**
     * 字段下拉框内容，如果不是下拉框字段，为空数组
     **/
    private final String[] select;


}
