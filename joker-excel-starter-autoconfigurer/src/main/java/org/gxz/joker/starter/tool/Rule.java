package org.gxz.joker.starter.tool;

import lombok.Data;
import org.gxz.joker.starter.annotation.ExcelField;
import org.gxz.joker.starter.config.ExcelFieldDescription;
import org.gxz.joker.starter.config.JokerConfigurationDelegate;
import org.gxz.joker.starter.convert.Converter;
import org.gxz.joker.starter.convert.ConverterRegistry;
import org.gxz.joker.starter.convert.UniqueConverter;

import java.lang.reflect.Field;

@Data
public class Rule implements Comparable<Rule> {

    String fieldName;

    Converter converter;

    String[] select;

    int width;
    /**
     * 表头名
     **/
    String cellName;

    int order;

    Class<?> fieldType;

    boolean unique;

    String errorMessage;

    private void init(ExcelField excelField, Class<?> fieldType) {
        this.order = excelField.order();
        this.cellName = excelField.name().isEmpty() ? this.fieldName : excelField.name();
        this.cellName = cellNameEnhance(cellName, excelField);
        Class<? extends Converter> converterClass = excelField.converter();
        ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
        Converter converterByType = converterRegistry.getConverterByType(converterClass);
        if (converterByType == null) {
            try {
                converterRegistry.putCustom(converterClass, converterClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        this.converter = converterRegistry.getConverterByType(converterClass);
        this.unique = excelField.unique();
        if (this.unique) {
            this.converter = new UniqueConverter(this.converter);
        }
        this.width = excelField.width();
        this.select = excelField.select();
        this.fieldType = fieldType;

        this.errorMessage = excelField.errorMessage();
    }

    private String cellNameEnhance(String cellName, ExcelField excelField) {
        ExcelFieldDescription desc = new ExcelFieldDescription(excelField);
        String prefix = JokerConfigurationDelegate.getPrefix(desc);
        String suffix = JokerConfigurationDelegate.getSuffix(desc);
        return prefix + cellName + suffix;
    }

    private void init(Class<?> fieldType) {
        this.width = 4000;
        this.select = new String[]{};
        this.converter = ConverterRegistry.DEFAULT_CONVERTER;
        this.order = -1;
        this.cellName = this.fieldName;
        this.fieldType = fieldType;
        this.errorMessage = "";
    }


    public Rule(Field field) {
        this.fieldName = field.getName();
        if (field.isAnnotationPresent(ExcelField.class)) {
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            this.init(excelField, field.getType());
        } else {
            this.init(field.getType());
        }
    }


    @Override
    public int compareTo(Rule rule) {
        return Integer.compare(this.order, rule.order);
    }
}
