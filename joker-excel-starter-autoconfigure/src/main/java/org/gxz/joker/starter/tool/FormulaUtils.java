package org.gxz.joker.starter.tool;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;


/**
 * 公式工具类
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class FormulaUtils {


    /**
     * @param sheet      sheet
     * @param colIndex   第几列
     * @param itemNumber 元素数量有多少
     * @return 返回表达式 例：sheet!$D$8:$D$18
     **/
    public static String columnNameFormula(Sheet sheet, int colIndex, int itemNumber) {
        if (itemNumber < 1) {
            throw new IllegalArgumentException("itemNumber 不能少于1");
        }
        String sheetName = sheet == null ? "" : sheet.getSheetName();
        if (!sheetName.isEmpty()) {
            sheetName = sheetName + "!";
        }
        String columnString = CellReference.convertNumToColString(colIndex);
        //  $D$1:$D$18
        return sheetName + "$" + columnString + "$2:$" + columnString + "$" + (itemNumber + 1);
    }


    /**
     * 行表达式
     *
     * @param sheet      sheet
     * @param colIndex   从第几列开始
     * @param itemNumber 一共多少个
     * @return 返回行 表达式 例: sheet!$C$1:$E$1
     **/
    public static String rowNameFormula(Sheet sheet, int colIndex, int itemNumber) {
        if (itemNumber < 1) {
            throw new IllegalArgumentException("itemNumber 不能少于1");
        }
        String sheetName = sheet == null ? "" : sheet.getSheetName();
        if (!sheetName.isEmpty()) {
            sheetName = sheetName + "!";
        }
        String lastStr = CellReference.convertNumToColString(colIndex + itemNumber - 1);
        String firstStr = CellReference.convertNumToColString(colIndex);
        return sheetName + "$" + firstStr + "$1:$" + lastStr + "$1";
    }


}
