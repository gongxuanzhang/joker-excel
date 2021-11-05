package org.gxz.joker.starter.model.description;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 描述一个单元格内容
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface CellDescription extends Description {


    /**
     *
     * 原始单元格
     * @return 拿到原始单元格
     **/
    Cell getRaw();

    /**
     *
     * 单元格的数据类型
     * @return 返回个啥
     **/
    Class<?> getCellType();

    /**
     *
     * 解析之后的数据内容
     * @return 返回具体数据
     **/
    Object getData();

}
