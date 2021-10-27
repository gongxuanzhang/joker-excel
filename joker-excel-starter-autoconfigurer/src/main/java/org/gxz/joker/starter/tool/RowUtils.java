package org.gxz.joker.starter.tool;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class RowUtils {

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
}
