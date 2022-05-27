package org.gxz.joker.starter.service;

import org.gxz.joker.starter.annotation.ExcelField;
import org.gxz.joker.starter.config.ExcelFieldDescription;
import org.gxz.joker.starter.config.build.JokerConfigurationDelegate;
import org.gxz.joker.starter.convert.Converter;
import org.gxz.joker.starter.convert.ConverterRegistry;
import org.gxz.joker.starter.element.DefaultValueConstant;

import java.lang.reflect.Field;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ColumnRuleFactory {

    private ColumnRuleFactory() {

    }


    /**
     * 通过一个属性创建一列的规则
     *
     * @param field 属性
     * @return 列规则
     **/
    public static ColumnRule createColumnRule(Field field) {
        HeadRule headRule;
        DataRule dataRule;
        ColumnRule columnRule = new ColumnRule();
        if (field.isAnnotationPresent(ExcelField.class)) {
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            headRule = createHead(field, excelField);
            dataRule = createDataRule(field, excelField);
            columnRule.setOrder(excelField.order());
        } else {
            headRule = createHead(field);
            dataRule = createDataRule(field, null);
            columnRule.setOrder(DefaultValueConstant.ORDER);
        }
        return columnRule.setHeadRule(headRule).setDataRule(dataRule);
    }


    private static DataRule createDataRule(Field field, ExcelField excelField) {
        DataRule dataRule = new DataRule();
        Converter<?> converter;
        ConverterRegistry cr = ConverterRegistry.getInstance();
        if (excelField == null) {
            converter = cr.getConverterByType(field.getType());
            dataRule.setExcelFieldDescription(new ExcelFieldDescription());
        } else {
            converter = cr.getConverterAndEnrol(excelField.converter());
            dataRule.setExcelFieldDescription(new ExcelFieldDescription(excelField));
        }
        dataRule.setConverter(converter).setFiledType(field.getType());
        return dataRule.setFieldName(field.getName());
    }


    // 创建表头规则

    private static HeadRule createHead(Field field) {
        HeadRule headRule = new HeadRule();
        String headName = field.getName();
        headName = cellNameEnhance(headName, null);
        int width = DefaultValueConstant.CELL_WIDTH;
        //// TODO: 2021/11/3 差个样式
        return headRule.setName(headName).setWidth(width);
    }


    private static HeadRule createHead(Field field, ExcelField excelField) {
        HeadRule headRule = new HeadRule();
        String headName = excelField.name().isEmpty() ? field.getName() : excelField.name();
        headName = cellNameEnhance(headName, excelField);
        int width = excelField.width();
        //// TODO: 2021/11/3 差个样式
        return headRule.setName(headName).setWidth(width);
    }


    private static String cellNameEnhance(String cellName, ExcelField excelField) {
        ExcelFieldDescription desc;
        if (excelField == null) {
            desc = new ExcelFieldDescription();
        } else {
            desc = new ExcelFieldDescription(excelField);
        }
        String prefix = JokerConfigurationDelegate.getPrefix(desc);
        String suffix = JokerConfigurationDelegate.getSuffix(desc);
        return prefix + cellName + suffix;
    }


}
