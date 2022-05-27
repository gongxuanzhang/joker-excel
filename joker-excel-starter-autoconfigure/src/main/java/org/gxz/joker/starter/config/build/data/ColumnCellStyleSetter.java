package org.gxz.joker.starter.config.build.data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 列中设置
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ColumnCellStyleSetter implements CellStyleSetter {

    /**
     * 这是表头在实体中的属性名，不是表头标识的名字
     **/
    protected final String head;

    public ColumnCellStyleSetter(String head) {
        this.head = head;
    }

    @Override
    public void setCellStyle(Cell cell, CellStyle style) {

    }

    @Override
    public CellStyle getCellStyle() {
        return null;
    }
}
