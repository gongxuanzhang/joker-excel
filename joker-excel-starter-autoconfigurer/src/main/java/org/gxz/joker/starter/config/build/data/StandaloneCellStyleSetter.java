package org.gxz.joker.starter.config.build.data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 独立设置一个单元格的实现
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class StandaloneCellStyleSetter implements CellStyleSetter {


    @Override
    public void setCellStyle(Cell cell, CellStyle style) {

    }

    @Override
    public CellStyle getCellStyle() {
        return null;
    }
}
