package org.gxz.joker.starter.tool;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class PoiUtils {

    public static void copyRow(Row source, Row target) {
        for (int i = 0; i < (int) source.getLastCellNum(); i++) {
            Cell sourceCell = source.getCell(i);
            if(sourceCell != null){
                Cell targetCell = target.createCell(i);
                Object cellValue = getCellValue(sourceCell);
                CellType cellTypeEnum = sourceCell.getCellTypeEnum();
                switch (cellTypeEnum) {
                    case STRING:
                        targetCell.setCellValue((String) cellValue);
                        break;
                    case NUMERIC:
                        targetCell.setCellValue((double) cellValue);
                        break;
                    case FORMULA:
                        targetCell.setCellFormula((String) cellValue);
                    default:
                        break;
                }
            }
        }

    }

    public static void appendCell(Row row, String value) {
        short lastCellNum = row.getLastCellNum();
        appendCell(row, value, lastCellNum);
    }

    public static void appendCell(Row row, String value, int cellIndex) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
    }

    public static Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType cellTypeEnum = cell.getCellTypeEnum();
        switch (cellTypeEnum) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BLANK:
                return "";
            default:
                return null;
        }
    }

}
