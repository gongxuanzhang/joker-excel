package org.gxz.joker.starter.tool;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class PoiUtils {

    public static void copyRow(Row source, Row target) {
        for (int i = 0; i < (int) source.getLastCellNum(); i++) {
            Cell sourceCell = source.getCell(i);
            if (sourceCell != null) {
                Cell targetCell = target.createCell(i);
                Object cellValue = getCellValue(sourceCell);
                CellType cellTypeEnum = sourceCell.getCellType();
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
        CellType cellTypeEnum = cell.getCellType();
        switch (cellTypeEnum) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case NUMERIC:
                double numericCellValue = cell.getNumericCellValue();
                return Double.toString(numericCellValue);
            case BLANK:
                return "";
            default:
                return null;
        }
    }


    public static Cell getCellOrCreate(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell != null) {
            return cell;
        }
        return row.createCell(cellIndex);
    }

    public static Row getRowOrCreate(Sheet sheet, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row != null) {
            return row;
        }
        return sheet.createRow(rowIndex);
    }
}
