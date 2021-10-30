package org.gxz.joker.starter.tool;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class PoiUtils {

    public static void copyRow(Row source, Row target) {
        for (int i = 0; i < (int) source.getLastCellNum(); i++) {
            Cell targetCell = target.createCell(i);
            Cell sourceCell = source.getCell(i);
            CellType cellTypeEnum = sourceCell.getCellTypeEnum();
            switch (cellTypeEnum) {
                case STRING:
                    targetCell.setCellValue(sourceCell.getStringCellValue());
                    break;
                case NUMERIC:
                    targetCell.setCellValue(sourceCell.getNumericCellValue());
                    break;
                default:
                    break;
            }
        }

    }

    public static void appendCell(Row row, String value){
        short lastCellNum = row.getLastCellNum();
        appendCell(row,value,lastCellNum);
    }

    public static void appendCell(Row row, String value,int cellIndex){
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
    }

    public static Object getCellValue(Cell cell){
        if(cell == null){
            return null;
        }
        CellType cellTypeEnum = cell.getCellTypeEnum();
        switch (cellTypeEnum){
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case NUMERIC:
                return cell.getNumericCellValue();
            default:
                return null;
        }
    }

}
