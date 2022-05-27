package org.gxz.joker.starter.element;

import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

import static org.gxz.joker.starter.tool.PoiUtils.getCellOrCreate;
import static org.gxz.joker.starter.tool.PoiUtils.getRowOrCreate;

/**
 * 独立列的动态选择框
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
public class IndependentDynamic {

    /**
     * 列名
     **/
    private String colName;
    /**
     * 列号 优先级高
     **/
    private Integer index;

    /**
     * 具体的元素信息
     **/
    private List<String> items;


    /**
     * 填充列
     * @param head 表头
     * @param colIndex 列号
     * @param sheet sheet
     **/
    public void fillCol(String head, int colIndex, Sheet sheet){
        Row firstRow = getRowOrCreate(sheet, 0);
        Cell currentCell = getCellOrCreate(firstRow, colIndex);
        currentCell.setCellValue(head);
        for (int i = 0; i < items.size(); i++) {
            Row row = getRowOrCreate(sheet, i + 1);
            Cell itemCell = getCellOrCreate(row, colIndex);
            itemCell.setCellValue(items.get(i));
        }


    }


    public int findInfoIndex(FieldInfo[] infos) {
        if (this.index != null) {
            Assert.isTrue(this.index < infos.length, "index大于列数");
            return this.index;
        }
        for (int i = 0; i < infos.length; i++) {
            if (Objects.equals(colName, infos[i].getExportColumnName())) {
                return i;
            }
        }
        throw new IllegalArgumentException("找不到对应列");
    }

    /**
     *
     * 在某一列写内容
     **/
    public void writeCell(){

    }




}
