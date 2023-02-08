package org.gxz.joker.starter.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gxz.joker.starter.component.BaseUploadCheck;
import org.gxz.joker.starter.component.ReadHolder;
import org.gxz.joker.starter.config.ReadConfig;
import org.gxz.joker.starter.config.build.JokerCallBackCombination;
import org.gxz.joker.starter.config.build.JokerConfigurationDelegate;
import org.gxz.joker.starter.convert.Converter;
import org.gxz.joker.starter.element.Checkable;
import org.gxz.joker.starter.exception.CheckValueException;
import org.gxz.joker.starter.exception.ConvertException;
import org.gxz.joker.starter.exception.RowExceedException;
import org.gxz.joker.starter.tool.ExportUtils;
import org.gxz.joker.starter.tool.PoiUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class AbstractExcelReader<T> implements ExcelReader<T> {


    /**
     * 解析的类型
     **/
    protected final Class<T> beanType;
    /**
     * 数据内容
     **/
    protected final List<T> data = new ArrayList<>();

    /**
     * 错误行
     **/
    protected final List<ErrorRow> errorRows = new ArrayList<>();

    private Map<Integer, String> errorCandidate;

    /**
     * 表头行
     **/
    protected Row head;

    private final ReadConfig readConfig;

    public AbstractExcelReader(Class<T> beanType, ReadConfig readConfig) {
        this.beanType = beanType;
        this.readConfig = readConfig;
    }


    @Override
    public ReadHolder<T> read(InputStream inputStream) throws IOException {
        // 读取出excel
        Workbook workbook = readWorkbook(inputStream);

        Sheet sheetAt = workbook.getSheetAt(0);

        // 获得拍好序的规则集合 如果列没有规则或者不解析，直接填充null值
        List<ColumnRule> sortRules = getSortRule(sheetAt);

        // 解析数据
        resolveData(sheetAt, sortRules);
        return new ReadHolder<>(this.data, this.errorRows, head);

    }

    /**
     * 从流中读取excel
     *
     * @param inputStream 输入流
     * @return 读取的excel
     **/
    protected Workbook readWorkbook(InputStream inputStream) throws IOException {
        return new XSSFWorkbook(inputStream);
    }

    /**
     * 拿到排好序的规则列表 数组中可能为null
     *
     * @param sheetAt sheet
     * @return 返回拍好序的规则，和表头一一对应
     **/
    protected List<ColumnRule> getSortRule(Sheet sheetAt) {
        this.head = sheetAt.getRow(0);
        String[] excelFields = ExportUtils.getExcelFields(this.beanType);
        if (excelFields.length == 0) {
            throw new IllegalStateException(beanType.getName() + "无法解析出内容");
        }
        List<ColumnRule> columnRules = ExportUtils.analysisRule(beanType, excelFields);
        Map<String, ColumnRule> hc = columnRules.stream().collect(Collectors.toMap(ColumnRule::getHeadValue, x -> x));
        // sort and fill
        List<ColumnRule> list = new ArrayList<>();
        for (int i = 0; i < (int) head.getLastCellNum(); i++) {
            Cell cell = head.getCell(i);
            if (cell != null) {
                String headValue = cell.getStringCellValue();
                ColumnRule columnRule = hc.get(headValue);
                if (columnRule != null) {
                    columnRule.setOrder(i);
                }
                // 可能为null
                list.add(columnRule);
            }
        }
        return list;
    }


    /**
     * 解析数据方法
     *
     * @param sheet     sheet
     * @param sortRules 拍完序的规则
     **/
    private void resolveData(Sheet sheet, List<ColumnRule> sortRules) {
        boolean haveError = false;
        // 这个list会填充正确数据中的角标和excel的行的对应关系
        List<Integer> indexMap = new ArrayList<>();
        // 循环内容   从第二行开始
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                continue;
            }
            int limit = getReadConfig().getLimit();
            if (limit != -1 && rowIndex > limit) {
                haveError = true;
                RowExceedException e = new RowExceedException("超过了限定行数" + limit);
                this.errorRows.add(new ErrorRow(row, e.getMessage()));
                JokerCallBackCombination.uploadRowError(row, e);
                continue;
            }
            JSONObject bean = new JSONObject();
            for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                try {
                    // 如果拿到的规则是null  说明此列是不需要解析的列  直接跳过即可
                    ColumnRule columnRule = sortRules.get(cellIndex);
                    if (columnRule == null) {
                        continue;
                    }
                    DataRule dataRule = columnRule.getDataRule();
                    Cell cell = row.getCell(cellIndex);

                    Object filedValue = calcFieldValue(cell, dataRule);
                    JokerConfigurationDelegate.check(columnRule, filedValue);
                    bean.put(dataRule.getFieldName(), filedValue);
                } catch (Exception e) {
                    haveError = true;
                    this.errorRows.add(new ErrorRow(row, e.getMessage()));
                    bean = null;
                    JokerCallBackCombination.uploadRowError(row, e);
                    // TODO: 2021/11/3 可以记录异常行数之类的信息
                    break;
                }
            }
            // 自检
            if (bean != null) {
                T t = bean.toJavaObject(beanType);
                try {
                    checkDataSelf(t);
                } catch (CheckValueException e) {
                    addCandidate(e, rowIndex);
                    continue;
                }
                indexMap.add(data.size(), rowIndex);
                this.data.add(t);

            }
        }


        // 配置检查
        List<BaseUploadCheck> uploadChecks = getUploadChecks();
        Map<Integer, String> errorRowCandidate = new HashMap<>(16);
        if (!CollectionUtils.isEmpty(uploadChecks)) {
            for (BaseUploadCheck uploadCheck : uploadChecks) {
                uploadCheck.register(data);
            }
            Iterator<T> iterator = data.iterator();
            int itIndex = 0;
            while (iterator.hasNext()) {
                T next = iterator.next();
                try {
                    for (BaseUploadCheck uploadCheck : uploadChecks) {
                        uploadCheck.uploadCheck(next);
                    }
                } catch (Exception e) {
                    errorRowCandidate.put(indexMap.get(itIndex), e.getMessage());
                    iterator.remove();
                }
                itIndex++;
            }
        }
        errorRowCandidate.forEach((index, errorMessage) -> {
            Row row = sheet.getRow(index);
            this.errorRows.add(new ErrorRow(row, errorMessage));
        });
        if (haveError) {
            JokerCallBackCombination.uploadFinish(data);
        } else {
            JokerCallBackCombination.uploadSuccess(data);
        }
    }

    private void checkDataSelf(T t) throws CheckValueException {
        if (t instanceof Checkable) {
            ((Checkable) t).check();
        }
    }

    protected List<BaseUploadCheck> getUploadChecks() {
        return null;
    }

    private void addCandidate(Exception e, int errorRowNum) {
        if (this.errorCandidate == null) {
            this.errorCandidate = new HashMap<>(16);
        }
        this.errorCandidate.put(errorRowNum, e.getMessage());
    }


    /**
     * 通过单元格和数据规则计算出最终填充到实体中的数据
     *
     * @param cell     单元格
     * @param dataRule 数据规则
     * @return 填充到
     **/
    protected Object calcFieldValue(Cell cell, DataRule dataRule) throws ConvertException {
        Object cellValue = PoiUtils.getCellValue(cell);
        if (cellValue == null) {
            return null;
        }
        Converter converter = dataRule.getConverter();
        String cellStringValue = cellValue.toString();
        Class<?> filedType = dataRule.getFiledType();
        try {
            return converter.reconvert(cellValue.toString(), dataRule.getFiledType());
        } catch (Exception e) {
            throw new ConvertException(converter, cellStringValue, filedType);
        }

    }


    public ReadConfig getReadConfig() {
        return readConfig;
    }

}
