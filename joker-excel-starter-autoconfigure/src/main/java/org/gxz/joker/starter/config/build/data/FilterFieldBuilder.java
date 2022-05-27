package org.gxz.joker.starter.config.build.data;

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.function.Predicate;

/**
 * 方便与用户链式编码
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class FilterFieldBuilder {

    private final Predicate<Object> filter;

    private CellStyle cellStyle;

    public FilterFieldBuilder(Predicate<Object> filter) {
        this.filter = filter;
    }

    public FilterFieldBuilder setCellStyle(CellStyle style) {
        this.cellStyle = style;
        return this;
    }
}
