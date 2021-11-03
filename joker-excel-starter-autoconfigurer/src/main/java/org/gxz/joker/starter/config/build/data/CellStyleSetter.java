package org.gxz.joker.starter.config.build.data;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 可以设置单元格样式的接口
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface CellStyleSetter {

    /***
     *
     * 给单元格设置样式
     * @param cell  单元格本身
     * @param style 样式本身 可以为空
     *
     **/
    void setCellStyle(Cell cell, CellStyle style);


    /**
     * 拿到要设置的单元格样式
     *
     * @return 拿到要设置的单元格样式
     **/
    CellStyle getCellStyle();
}
