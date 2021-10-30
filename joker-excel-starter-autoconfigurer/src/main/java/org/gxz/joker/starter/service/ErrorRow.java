package org.gxz.joker.starter.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
@AllArgsConstructor
public class ErrorRow {

    private final Row row;

    private final  String errorMessage;


}
