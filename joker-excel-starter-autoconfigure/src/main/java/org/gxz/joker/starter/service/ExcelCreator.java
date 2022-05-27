package org.gxz.joker.starter.service;


import org.apache.poi.ss.usermodel.Workbook;



/**
 * 写excel
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface ExcelCreator {

    /**
     * 创建一个excel
     * @return 返回创建的excel
     **/
    Workbook create();


}
