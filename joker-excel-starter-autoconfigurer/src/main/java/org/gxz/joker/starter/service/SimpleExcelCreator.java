package org.gxz.joker.starter.service;

import org.gxz.joker.starter.element.ExcelDescription;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class SimpleExcelCreator extends AbstractExcelCreator {

    public SimpleExcelCreator(Iterable<?> data, ExcelDescription excelDescription) {
        super(data, excelDescription);
    }
}
