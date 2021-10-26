package org.gxz.joker.starter.exception;

import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * 导入导出过程中出现的问题
 * @author gxz gongxuanzhang@foxmail.com
 *
 *
 **/
@Getter
public class ExcelException extends RuntimeException {

    /**
     * 解析错误的行数 注意是从第1行开始的
     **/
    private int rowCount;


    /**
     * 解析错误的列位置
     **/
    private int colCount;

    /**
     * 解析错误的单元格内容
     **/
    private String cellValue;

    public ExcelException(int rowCount, String errorMessage, int colCount, String cellValue) {
        super(errorMessageAnalysis(errorMessage, rowCount, colCount, cellValue));
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.cellValue = cellValue;
    }

    public ExcelException(String message) {
        super(message);
    }


    private static final String DEFAULT_ERROR_MESSAGE = "第r%行 第c%列 [v%] 解析错误";

    private static String errorMessageAnalysis(String errorMessage, int rowCount, int colCount, String cellValue) {
        if (StringUtils.isEmpty(errorMessage)) {
            return errorMessageAnalysis(DEFAULT_ERROR_MESSAGE, rowCount, colCount, cellValue);
        }
        return errorMessage.replaceAll("r%", rowCount + "").replaceAll("c%", colCount + "").replaceAll("v%", cellValue);
    }
}
