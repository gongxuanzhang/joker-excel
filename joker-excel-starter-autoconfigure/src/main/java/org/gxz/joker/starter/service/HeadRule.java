package org.gxz.joker.starter.service;

import lombok.Data;
import org.gxz.joker.starter.config.build.data.CellStyleSetter;

/**
 * excel表头信息
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
public class HeadRule {

    /**
     * 表头名称
     **/
    private String name;

    /**
     * 表头长度
     **/
    private int width;

    /**
     * 表头样式设置
     **/
    private CellStyleSetter styleSetter;
}
