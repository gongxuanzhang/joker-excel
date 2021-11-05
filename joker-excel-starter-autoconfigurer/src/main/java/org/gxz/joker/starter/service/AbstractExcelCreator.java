package org.gxz.joker.starter.service;


import org.apache.commons.collections4.IterableUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gxz.joker.starter.config.build.JokerConfigurationDelegate;
import org.gxz.joker.starter.convert.Converter;
import org.gxz.joker.starter.element.Checkable;
import org.gxz.joker.starter.element.ExcelDescription;
import org.gxz.joker.starter.exception.CheckValueException;
import org.gxz.joker.starter.exception.ConvertException;
import org.gxz.joker.starter.tool.ExportUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class AbstractExcelCreator implements ExcelCreator {

    protected final List<ColumnRule> rules;

    protected final ExcelDescription excelDescription;

    protected final Iterable<?> data;

    public AbstractExcelCreator(Iterable<?> data, ExcelDescription excelDescription) {
        this.excelDescription = excelDescription;
        this.data = data;
        this.rules = transform(excelDescription);
    }

    private List<ColumnRule> transform(ExcelDescription excelDescription) {
        String[] excelFields = ExportUtils.getExcelFields(excelDescription);
        List<ColumnRule> columnRules = ExportUtils.analysisRule(excelDescription.getBeanType(), excelFields);
        Collections.sort(columnRules);
        return columnRules;
    }


    @Override
    public Workbook create() {
        XSSFWorkbook excel = ExportUtils.createEmptySheetExcel("sheet");
        XSSFSheet sheet = excel.getSheetAt(0);
        setHead(sheet.createRow(0));
        int rowNum = 1;
        if (!IterableUtils.isEmpty(data)) {
            for (Object rowData : data) {
                Row row = sheet.createRow(rowNum);
                try {
                    setRow(row, rowData, rules);
                    rowNum++;
                } catch (ConvertException e) {
                    sheet.removeRow(row);
                    // TODO: 2021/10/25  这里是导出回调
                }
            }
            JokerConfigurationDelegate.clip(sheet, rules);
        }
        return excel;
    }


    private void setRow(Row row, Object object, List<ColumnRule> rules) throws CheckValueException {
        for (int i = 0; i < rules.size(); i++) {
            ColumnRule rule = rules.get(i);
            Cell cell = row.createCell(i);
            Class<?> clazz = object.getClass();
            cell.setCellValue(getValueByRule(clazz, object, rule));
        }
    }


    private static String getValueByRule(Class<?> clazz, Object object, ColumnRule rule) throws CheckValueException {
        Field field;
        try {
            field = clazz.getDeclaredField(rule.getDataRule().getFieldName());
            field.setAccessible(true);
            Object value = field.get(object);
            Converter converter = rule.getDataRule().getConverter();
            return converter.convert(value);
        } catch (NoSuchFieldException | IllegalAccessException | ConvertException e) {
            throw new CheckValueException(e.getMessage());
        }
    }

    protected void setHead(Row head) {
        // 设置表头
        for (int i = 0; i < this.rules.size(); i++) {
            ColumnRule columnRule = rules.get(i);
            Cell cell = head.createCell(i);
            cell.setCellValue(columnRule.getHeadValue());
        }
    }


}
