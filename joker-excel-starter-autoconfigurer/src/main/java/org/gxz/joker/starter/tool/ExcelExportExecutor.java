package org.gxz.joker.starter.tool;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gxz.joker.starter.annotation.ExcelData;
import org.gxz.joker.starter.annotation.ExcelField;
import org.gxz.joker.starter.component.AnalysisDataHolder;
import org.gxz.joker.starter.component.BaseUploadCheck;
import org.gxz.joker.starter.config.JokerCallBackCombination;
import org.gxz.joker.starter.config.JokerConfigurationDelegate;
import org.gxz.joker.starter.convert.Converter;
import org.gxz.joker.starter.element.Checkable;
import org.gxz.joker.starter.element.ExcelDescription;
import org.gxz.joker.starter.element.FieldHolder;
import org.gxz.joker.starter.exception.ConvertException;
import org.gxz.joker.starter.exception.ExcelException;
import org.gxz.joker.starter.service.ErrorRow;
import org.gxz.joker.starter.service.Rule;

import javax.crypto.MacSpi;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 导出执行器
 **/
public class ExcelExportExecutor {


    public static <T> Workbook writeWorkBook(Iterable<T> targetData, ExcelDescription excelDescription) {
        Class<?> beanType = excelDescription.getBeanType();
        ExcelInfo excelInfo = analysisExportRule(beanType, excelDescription);
        XSSFWorkbook excel = ExportUtils.createEmptySheetExcel("sheet");
        XSSFSheet sheet = excel.getSheetAt(0);
        // 设置表头
        Row headRow = sheet.createRow(0);
        String[] head = excelInfo.getHead();
        for (int i = 0; i < head.length; i++) {
            headRow.createCell(i).setCellValue(head[i]);
        }
        List<Rule> rules = excelInfo.getRules();

        int rowNum = 1;
        for (T rowData : targetData) {
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
        return excel;
    }


    public static <T> AnalysisDataHolder<T> readWorkBook(Workbook workbook, Class<T> clazz, String checkId) {
        ExcelInfo excelInfo = analysisExportRule(clazz, null);
        List<Rule> rules = excelInfo.getRules();
        Sheet sheet = workbook.getSheetAt(0);
        return analysis(sheet, rules, clazz, checkId);
    }


    /**
     * 这个方法在列改变顺序的情况下仍然能正确解析
     **/
    private static <T> AnalysisDataHolder<T> analysis(Sheet sheet, List<Rule> rules, Class<T> clazz, String checkId) {
        List<T> data = new ArrayList<>();
        List<ErrorRow> errorRows = new ArrayList<>();
        Map<String, Rule> ruleMap = rules.stream().collect(Collectors.toMap(Rule::getCellName, Function.identity()));
        Map<Integer, Rule> orderRule = new HashMap<>(16);
        boolean haveError = false;
        Row headRow = sheet.getRow(0);
        Map<Integer,String> errorRowCandidate = new HashMap<>();
        Map<Integer, Integer> listErrorTable = new HashMap<>();
        BaseUploadCheck uploadCheck = JokerConfigurationDelegate.uploadCheck(checkId);
        // 设置表头
        for (int i = 0; i < headRow.getLastCellNum(); i++) {
            Cell cell = headRow.getCell(i);
            String headString = cell.getStringCellValue();
            if (ruleMap.containsKey(headString)) {
                orderRule.put(i, ruleMap.get(headString));
            }
        }
        // 设置内容
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                continue;
            }
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
                    JokerConfigurationDelegate.check(cellRule, value);
                    jsonObject.put(cellRule.getFieldName(), value);
                } catch (ExcelException e) {
                    errorRows.add(new ErrorRow(row,e.getMessage()));
                    cellError = true;
                    haveError = true;
                    JokerCallBackCombination.uploadRowError(row, e);
                    break;
                } catch (Exception e) {
                    errorRows.add(new ErrorRow(row,e.getMessage()));
                    cellError = true;
                    haveError = true;
                    JokerCallBackCombination.uploadRowError(row, new ExcelException(rowIndex + 1,
                            cellRule.getErrorMessage(), cellIndex + 1,
                            cell.getStringCellValue()));
                    break;
                }
            }
            // 如果成功转换，最后还需要校验
            if (!cellError) {
                T rowData = jsonObject.toJavaObject(clazz);
                if (rowData instanceof Checkable) {
                    try {
                        ((Checkable<?>) rowData).check();
                    } catch (ConvertException e) {
                        errorRowCandidate.put(rowIndex,e.getMessage());
                        continue;
                    }
                }
                listErrorTable.put(data.size(), rowIndex);
                data.add(rowData);
            }
        }
        if (uploadCheck != null) {
            uploadCheck.register(data);
            Iterator<T> iterator = data.iterator();
            int itIndex = 0;
            while (iterator.hasNext()) {
                T next = iterator.next();
                try {
                    uploadCheck.uploadCheck(next);
                } catch (Exception e) {
                    errorRowCandidate.put(listErrorTable.get(itIndex),e.getMessage());
                    iterator.remove();
                }
                itIndex++;
            }
        }
        errorRowCandidate.forEach((index,message) -> {
            Row err = sheet.getRow(index);
            errorRows.add(new ErrorRow(err, message));
        });
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
            if (Checkable.class.isAssignableFrom(clazz)) {
                ((Checkable) object).check();
            }
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


    private static ExcelInfo analysisExportRule(Class<?> beanType, ExcelDescription excelDescription) {
        ExcelInfo excelInfo = new ExcelInfo();
        String[] excelFields = getExcelFields(beanType, excelDescription);
        if (excelFields.length == 0) {
            throw new IllegalStateException(beanType.getName() + "无法解析出内容");
        }

        List<Rule> rules = Arrays.stream(excelFields).map(excelField -> {
            try {
                return beanType.getDeclaredField(excelField);
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
