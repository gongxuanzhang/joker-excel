package org.gxz.joker.starter.tool;


import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gxz.joker.starter.annotation.ExcelData;
import org.gxz.joker.starter.annotation.ExcelField;
import org.gxz.joker.starter.element.ExcelDescription;
import org.gxz.joker.starter.element.FieldHolder;
import org.gxz.joker.starter.service.ColumnRule;
import org.gxz.joker.starter.service.ColumnRuleFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ExportUtils {

    private ExportUtils() {
        throw new RuntimeException("工具类禁止创建对象");
    }

    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            // throw new NormalException(e.getMessage());
        }
    }


    /**
     * 拿到必要的属性名
     **/
    private static Set<String> getMustField(Class<?> clazz, boolean allField) {
        Stream<Field> stream = Arrays.stream(clazz.getDeclaredFields());
        if (!allField) {
            stream = stream.filter(field -> field.isAnnotationPresent(ExcelField.class));
        }
        return stream.map(Field::getName).collect(Collectors.toSet());
    }


    /**
     * 解析ExcelData {@link ExcelData}
     *
     * @return 返回需要导出导入的字段名称
     **/
    public static String[] getExcelFields(ExcelDescription excelDescription) {
        Class<?> clazz = excelDescription.getBeanType();
        FieldHolder fieldHolder = excelDescription.getFieldHolder();
        Set<String> fields = getMustField(clazz, fieldHolder.isAllField());
        if (clazz.isAnnotationPresent(ExcelData.class)) {
            String[] ignore, include;
            ignore = fieldHolder.getIgnore();
            include = fieldHolder.getInclude();
            Set<String> allFields = new HashSet<>(fields);
            if (include.length == 0 && ignore.length == 0) {
                return allFields.toArray(new String[]{});
            }
            if (include.length != 0) {
                Set<String> includes = Arrays.stream(include).filter(allFields::contains).collect(Collectors.toSet());
                return includes.toArray(new String[]{});
            } else {
                for (String name : ignore) {
                    allFields.remove(name);
                }
                return allFields.toArray(new String[]{});
            }
        }
        return fields.toArray(new String[]{});
    }

    public static String[] getExcelFields(Class<?> clazz) {
        return getMustField(clazz, true).toArray(new String[]{});
    }

    public static XSSFWorkbook createEmptySheetExcel(String sheetName) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 设置样式
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("@"));
        workbook.createSheet(sheetName);
        return workbook;
    }


    public static List<ColumnRule> analysisRule(Class<?> beanType, String[] excelFields) {
        return Arrays.stream(excelFields).map(excelField -> {
            try {
                return beanType.getDeclaredField(excelField);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).map(ColumnRuleFactory::createColumnRule).collect(Collectors.toList());
    }


}
