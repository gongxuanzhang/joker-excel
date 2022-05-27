package org.gxz.joker.starter.tool;

import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * 数据校验层工具类
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class ValidationUtils {


    /**
     * 默认校验2000列
     **/
    private static final int VALIDATION_ROW = 2000;


    /**
     * 创建一个列公式数据校验
     *
     * @param sheet      校验列所在sheet
     * @param colIndex   校验第几列
     * @param formula 公式
     * @return 列数据校验
     **/
    public static DataValidation createColValidationFormula(XSSFSheet sheet, int colIndex, String formula) {
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        CellRangeAddressList provRangeAddressList = new CellRangeAddressList(1, VALIDATION_ROW, colIndex, colIndex);
        DataValidationConstraint formulaListConstraint = dvHelper.createFormulaListConstraint(formula);
        DataValidation validation = dvHelper.createValidation(formulaListConstraint, provRangeAddressList);
        validationAddAlarm(validation);
        return validation;
    }

    /**
     * 给数据校验加上错误提示
     *
     * @param dataValidation 数据校验
     **/
    public static void validationAddAlarm(DataValidation dataValidation) {
        dataValidation.createErrorBox("警告", "请在下拉框里选择");
        dataValidation.setShowErrorBox(true);
        dataValidation.setSuppressDropDownArrow(true);
    }

}
