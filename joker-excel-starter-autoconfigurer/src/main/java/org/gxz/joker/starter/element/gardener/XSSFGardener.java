package org.gxz.joker.starter.element.gardener;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.gxz.joker.starter.tool.Rule;

import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class XSSFGardener implements Gardener {


    @Override
    public void clip(Sheet sheet, List<Rule> ruleList) {
        xssfClip((XSSFSheet) sheet, ruleList);
    }


    public abstract void xssfClip(XSSFSheet sheet, List<Rule> ruleList);

    @Override
    public boolean support(Sheet sheet, List<Rule> ruleList) {
        return sheet instanceof XSSFSheet;
    }
}
