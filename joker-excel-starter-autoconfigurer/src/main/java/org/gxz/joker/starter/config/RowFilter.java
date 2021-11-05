package org.gxz.joker.starter.config;


import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

/**
 * joker的过滤器顶层接口，一般使用在配置中
 * @author gxz gongxuanzhang@foxmail.com
 **/
@FunctionalInterface
public interface RowFilter<T> {


    /**
     *
     * 过滤
     * @param row 一行
     * @param rowData 解析成功的数据
     * @param cellStyle 样式
     * @return true 为命中
     **/
    boolean doFilter(Row row, T rowData, CellStyle cellStyle);
}
