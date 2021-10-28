package org.gxz.joker.starter.element.gardener;

import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.gxz.joker.starter.tool.Rule;

import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class SelectGardener extends XSSFGardener{


    @Override
    public void xssfClip(XSSFSheet sheet, List<Rule> ruleList) {
        // 设置长度和下拉框
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        for (int i = 0; i < ruleList.size(); i++) {
            sheet.setColumnWidth(i, ruleList.get(i).getWidth());
            String[] select = ruleList.get(i).getSelect();
            if (select.length > 0) {
                XSSFDataValidationConstraint assetTypeConstraint =
                        (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(select);
                CellRangeAddressList typeList = new CellRangeAddressList(1, sheet.getLastRowNum(), i, i);
                XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(assetTypeConstraint,
                        typeList);
                sheet.addValidationData(validation);
            }
        }
    }
}
