package org.gxz.joker.starter.service;

import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 抛出这个异常说明在excel解析的过程中出现了问题
 **/
@Getter
public class ExcelConverterException extends RuntimeException {

    /**
     * 解析错误的行数 注意是从第1行开始的
     **/
    private final int rowCount;


    /**
     * 解析错误的列位置
     **/
    private final int colCount;

    /**
     * 解析错误的单元格内容
     **/
    private final String cellValue;

    public ExcelConverterException(int rowCount, String errorMessage, int colCount, String cellValue) {
        super(errorMessageAnalysis(errorMessage, rowCount, colCount, cellValue));
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.cellValue = cellValue;
    }


    private static final String DEFAULT_ERROR_MESSAGE = "第r%行 第c%列 [v%] 解析错误";

    private static String errorMessageAnalysis(String errorMessage, int rowCount, int colCount, String cellValue) {
        if (StringUtils.isEmpty(errorMessage)) {
            return errorMessageAnalysis(DEFAULT_ERROR_MESSAGE, rowCount, colCount, cellValue);
        }
        return errorMessage.replaceAll("r%", rowCount + "").replaceAll("c%", colCount + "").replaceAll("v%", cellValue);
    }
}
