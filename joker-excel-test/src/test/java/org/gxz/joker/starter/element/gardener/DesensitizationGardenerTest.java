package org.gxz.joker.starter.element.gardener;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gxz.joker.starter.config.ExcelFieldDescription;
import org.gxz.joker.starter.service.ColumnRule;
import org.gxz.joker.starter.service.DataRule;
import org.gxz.joker.starter.tool.ExportUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


class DesensitizationGardenerTest {


    @Test
    @DisplayName("脱敏测试")
    public void desc() throws NoSuchFieldException, IllegalAccessException {
        DesensitizationGardener desensitizationGardener = new DesensitizationGardener();
        XSSFWorkbook excel = ExportUtils.createEmptySheetExcel("sheet");
        XSSFSheet test = excel.createSheet("test");

        for (int i = 1; i < 3; i++) {
            XSSFRow row = test.createRow(i);
            XSSFCell nameCell = row.createCell(0);
            XSSFCell emailCell = row.createCell(1);
            XSSFCell emailCell1 = row.createCell(2);
            XSSFCell emailCell2 = row.createCell(3);
            nameCell.setCellValue("sunwukong");
            emailCell.setCellValue("sunwukong@foxmail.com");
            emailCell1.setCellValue("sun@");
            emailCell2.setCellValue("sunwukong");

        }
        List<ColumnRule> columnRuleList = new ArrayList<>();
        DataRule nameRule = new DataRule();
        ExcelFieldDescription excelFieldDescription = new ExcelFieldDescription();
        Field field = excelFieldDescription.getClass().getDeclaredField("desensitizationExpression");
        field.setAccessible(true);
        field.set(excelFieldDescription,"1_3");
        nameRule.setExcelFieldDescription(excelFieldDescription);

        ExcelFieldDescription excelFieldDescription1 = new ExcelFieldDescription();
        field.set(excelFieldDescription1,"3~@");
        DataRule emailRule = new DataRule();
        emailRule.setExcelFieldDescription(excelFieldDescription1);

        columnRuleList.add(new ColumnRule().setDataRule(nameRule));
        columnRuleList.add(new ColumnRule().setDataRule(emailRule));
        columnRuleList.add(new ColumnRule().setDataRule(emailRule));
        columnRuleList.add(new ColumnRule().setDataRule(emailRule));
        desensitizationGardener.xssfClip(test, columnRuleList);
        for (Row cells : test) {
            Assertions.assertEquals(cells.getCell(0).getStringCellValue(), "s*****ong");
            Assertions.assertEquals(cells.getCell(1).getStringCellValue(), "sun*****@foxmail.com");
            Assertions.assertEquals(cells.getCell(2).getStringCellValue(), "sun@");
            Assertions.assertEquals(cells.getCell(3).getStringCellValue(), "sun******");
        }
    }

}
