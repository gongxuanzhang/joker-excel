package org.gxz.joker.starter.model.description;

import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 *
 * 描述一行的具体内容
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface RowDescription extends Description{


    /**
     *
     * 原行
     * @return raw from row
     **/
    Row getRaw();

    /**
     *
     * 解析之后的行数据
     * @return 行数据
     **/
    Object getData();

    /**
     *
     * 一行内容
     * @return 行数据规则
     **/
    List<CellDescription> getCellDescription();





}
