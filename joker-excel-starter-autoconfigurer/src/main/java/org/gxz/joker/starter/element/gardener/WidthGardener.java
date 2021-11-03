package org.gxz.joker.starter.element.gardener;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.gxz.joker.starter.service.ColumnRule;
import org.gxz.joker.starter.service.Rule;

import java.util.List;

/**
 * 长度工匠
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class WidthGardener extends XSSFGardener {

    @Override
    public void xssfClip(XSSFSheet sheet, List<ColumnRule> ruleList) {
        // 设置长度
        for (int i = 0; i < ruleList.size(); i++) {
            int width = ruleList.get(i).getHeadRule().getWidth();
            if (width < 0) {
                sheet.autoSizeColumn(i);
            } else {
                sheet.setColumnWidth(i, width);
            }
        }
    }
}
