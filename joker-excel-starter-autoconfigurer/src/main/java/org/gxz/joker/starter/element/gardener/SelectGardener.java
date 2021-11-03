package org.gxz.joker.starter.element.gardener;

import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.gxz.joker.starter.service.ColumnRule;
import org.gxz.joker.starter.service.Rule;

import java.util.List;

/**
 * 下拉框工匠
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class SelectGardener extends XSSFGardener {


    @Override
    public void xssfClip(XSSFSheet sheet, List<ColumnRule> ruleList) {
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        for (int i = 0; i < ruleList.size(); i++) {
            String[] select = ruleList.get(i).getDataRule().getExcelFieldDescription().getSelect();
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
