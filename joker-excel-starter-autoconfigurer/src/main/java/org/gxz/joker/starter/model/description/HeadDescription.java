package org.gxz.joker.starter.model.description;

import java.util.List;

/**
 * 一整行的表头描述
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface HeadDescription {

    /**
     *
     * 包含一行表头每一个单元格的描述(元素内容可以为空。表示某一行不解析)
     * @return 返回每一个单元格的描述
     **/
    List<CellDescription> getCellDescription();


}
