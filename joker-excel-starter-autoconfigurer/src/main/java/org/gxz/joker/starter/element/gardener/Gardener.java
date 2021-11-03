package org.gxz.joker.starter.element.gardener;

import org.apache.poi.ss.usermodel.Sheet;
import org.gxz.joker.starter.service.ColumnRule;
import org.gxz.joker.starter.service.Rule;

import java.util.List;

/**
 * 特殊样式的装饰者
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface Gardener {

    /**
     *
     * 给单元格加入样式
     * @param sheet sheet
     * @param ruleList 列规则的集合
     *
     **/
    void clip(Sheet sheet, List<ColumnRule> ruleList);


}
