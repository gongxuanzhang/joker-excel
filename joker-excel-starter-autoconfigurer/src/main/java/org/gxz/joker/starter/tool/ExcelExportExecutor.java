package org.gxz.joker.starter.tool;

import com.alibaba.fastjson.JSONObject;
import org.gxz.joker.starter.annotation.ExcelData;
import org.gxz.joker.starter.annotation.ExcelField;
import org.gxz.joker.starter.component.AnalysisDataHolder;
import org.gxz.joker.starter.config.JokerCallBackCombination;
import org.gxz.joker.starter.convert.Converter;
import org.gxz.joker.starter.convert.ConverterRegistry;
import org.gxz.joker.starter.convert.UniqueConverter;
import org.gxz.joker.starter.element.ExcelDescription;
import org.gxz.joker.starter.element.FieldHolder;
import org.gxz.joker.starter.exception.ConvertException;
import org.gxz.joker.starter.exception.ExcelException;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 导出执行器
 **/
public class ExcelExportExecutor {


    public static <T> Workbook writeWorkBook(List<T> lists, ExcelDescription excelDescription) {
        if (lists == null || lists.isEmpty()) {
            throw new NullPointerException("实体内容为空");
        }
        Class<?> clazz = lists.get(0).getClass();

        ExcelInfo excelInfo = analysisExportRule(clazz, excelDescription);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet");
        Row headRow = sheet.createRow(0);
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("@"));
        // 设置表头
        String[] head = excelInfo.getHead();
        for (int i = 0; i < head.length; i++) {
            headRow.createCell(i).setCellValue(head[i]);
        }
        List<Rule> rules = excelInfo.getRules();

        // 设置内容
        for (int i = 0; i < lists.size(); i++) {
            Row row = sheet.createRow(i + 1);
            try {
                setRow(row, lists.get(i), rules);
            } catch (ConvertException e) {
                sheet.removeRow(row);
                // TODO: 2021/10/25  这里是导出回调
            }
        }

        // 设置长度和下拉框
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        for (int i = 0; i < rules.size(); i++) {
            sheet.setColumnWidth(i, rules.get(i).getWidth());
            String[] select = rules.get(i).getSelect();
            if (select.length > 0) {
                XSSFDataValidationConstraint assetTypeConstraint =
                        (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(select);
                CellRangeAddressList typeList = new CellRangeAddressList(1, sheet.getLastRowNum(), i, i);
                XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(assetTypeConstraint,
                        typeList);
                sheet.addValidationData(validation);
            }
        }

        return workbook;
    }


    public static <T> AnalysisDataHolder<T> readWorkBook(XSSFWorkbook workbook, Class<T> clazz) {
        ExcelInfo excelInfo = analysisExportRule(clazz, null);
        List<Rule> rules = excelInfo.getRules();
        XSSFSheet sheet = workbook.getSheetAt(0);
        return analysis(sheet, rules, clazz);
    }


    /**
     * 这个方法在列改变顺序的情况下仍然能正确解析
     **/
    private static <T> AnalysisDataHolder<T> analysis(XSSFSheet sheet, List<Rule> rules, Class<T> clazz) {
        List<T> data = new ArrayList<>();
        List<Row> errorRows = new ArrayList<>();
        Map<String, Rule> ruleMap = rules.stream().collect(Collectors.toMap(Rule::getCellName, Function.identity()));
        Map<Integer, Rule> orderRule = new HashMap<>();
        boolean haveError = false;
        Row headRow = sheet.getRow(0);
        for (int i = 0; i < headRow.getLastCellNum(); i++) {
            Cell cell = headRow.getCell(i);
            String headString = cell.getStringCellValue();
            if (ruleMap.containsKey(headString)) {
                orderRule.put(i, ruleMap.get(headString));
            }
        }
        for (int rowIndex = 1; rowIndex < sheet.getLastRowNum(); rowIndex++) {
            XSSFRow row = sheet.getRow(rowIndex);
            JSONObject jsonObject = new JSONObject();
            boolean cellError = false;
            for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                Rule cellRule = orderRule.get(cellIndex);
                if (cellRule == null) {
                    continue;
                }
                Cell cell = row.getCell(cellIndex);
                try {
                    Object value = cellRule.getConverter().reconvert(cell.getStringCellValue(),
                            cellRule.getFieldType());
                    jsonObject.put(cellRule.getFieldName(), value);
                } catch (ExcelException e) {
                    errorRows.add(row);
                    cellError = true;
                    haveError = true;
                    JokerCallBackCombination.uploadRowError(row, e);
                    break;
                } catch (Exception e) {
                    errorRows.add(row);
                    cellError = true;
                    haveError = true;
                    JokerCallBackCombination.uploadRowError(row, new ExcelException(rowIndex + 1,
                            cellRule.errorMessage, cellIndex + 1,
                            cell.getStringCellValue()));
                    break;
                }
            }
            if (!cellError) {
                data.add(jsonObject.toJavaObject(clazz));
            }

        }
        if (haveError) {
            JokerCallBackCombination.uploadFinish(data);
        } else {
            JokerCallBackCombination.uploadSuccess(data);
        }
        return new AnalysisDataHolder<>(data, errorRows, headRow);
    }


    private static void setRow(Row row, Object object, List<Rule> rules) throws ConvertException {
        for (int i = 0; i < rules.size(); i++) {
            Rule rule = rules.get(i);
            Cell cell = row.createCell(i);
            Class<?> clazz = object.getClass();
            cell.setCellValue(getValueByRule(clazz, object, rule));
        }
    }

    private static String getValueByRule(Class<?> clazz, Object object, Rule rule) throws ConvertException {
        Field field;
        try {
            field = clazz.getDeclaredField(rule.getFieldName());
            field.setAccessible(true);
            Object value = field.get(object);
            Converter converter = rule.getConverter();
            return converter.convert(value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (ConvertException e) {
            throw e;
        }
        return "";
    }


    private static ExcelInfo analysisExportRule(Class<?> clazz, ExcelDescription excelDescription) {
        ExcelInfo excelInfo = new ExcelInfo();
        String[] excelFields = getExcelFields(clazz, excelDescription);
        if (excelFields.length == 0) {
            throw new IllegalStateException(clazz.getName() + "无法解析出内容");
        }

        List<Rule> rules = Arrays.stream(excelFields).map(excelField -> {
            try {
                return clazz.getDeclaredField(excelField);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).map(Rule::new).sorted().collect(Collectors.toList());
        excelInfo.setRules(rules);
        String[] heads = rules.stream().map(Rule::getCellName).collect(Collectors.toList()).toArray(new String[]{});
        excelInfo.setHead(heads);
        return excelInfo;
    }


    /**
     * 解析ExcelData {@link ExcelData}
     *
     * @return 返回需要返回的所有属性名
     * @author gxz
     **/
    private static String[] getExcelFields(Class<?> clazz, ExcelDescription excelDescription) {
        if (excelDescription == null) {
            return getMustField(clazz, true).toArray(new String[]{});
        }
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

    /**
     * 获取必须加入的属性名 (拥有ExcelField的)
     **/
    private static Set<String> getMustField(Class<?> clazz, boolean allField) {
        Stream<Field> stream = Arrays.stream(clazz.getDeclaredFields());
        if (!allField) {
            stream = stream.filter(field -> field.isAnnotationPresent(ExcelField.class));
        }
        return stream.map(Field::getName).collect(Collectors.toSet());
    }


    @Getter
    @Setter
    public static class ExcelInfo {

        private String[] head;

        private List<Rule> rules;

    }


}
