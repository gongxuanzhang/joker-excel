package org.gxz.joker.starter.element.gardener;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.gxz.joker.starter.service.ColumnRule;
import org.gxz.joker.starter.service.Rule;

import java.util.List;

/**
 * XSSF excel的内容
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class XSSFGardener implements Gardener {


    @Override
    public void clip(Sheet sheet, List<ColumnRule> ruleList) {
        xssfClip((XSSFSheet) sheet, ruleList);
    }

    /**
     * 包装clip方法  子类拿到的都是xssf sheet
     *
     * @param sheet    sheet
     * @param ruleList 规则
     **/
    public abstract void xssfClip(XSSFSheet sheet, List<ColumnRule> ruleList);

}
