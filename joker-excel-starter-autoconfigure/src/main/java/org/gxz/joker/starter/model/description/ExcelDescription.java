package org.gxz.joker.starter.model.description;

import org.springframework.lang.Nullable;

import java.util.List;

/**
 *
 *
 * excel描述
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface ExcelDescription extends Description{

    /**
     *
     * excel名
     * @return excel名称
     **/
    String getExcelName();

    /**
     *
     * excel的sheet的描述
     * @return 多个sheet的描述内容
     **/
    @Nullable
    List<SheetDescription> getSheetDescriptions();



}
