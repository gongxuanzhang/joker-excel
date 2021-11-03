package org.gxz.joker.starter.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.gxz.joker.starter.service.ErrorRow;

import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadHolder<T> {
    private List<T> data;
    private List<ErrorRow> errorRows;
    private Row head;

    public ReadHolder(List<T> data) {
        this.data = data;
    }
}
