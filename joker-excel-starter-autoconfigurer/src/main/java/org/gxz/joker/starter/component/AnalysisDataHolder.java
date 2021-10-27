package org.gxz.joker.starter.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisDataHolder<T> {
    private List<T> data;
    private List<Row> errorRows;
    private Row head;

    public AnalysisDataHolder(List<T> data) {
        this.data = data;
    }
}
