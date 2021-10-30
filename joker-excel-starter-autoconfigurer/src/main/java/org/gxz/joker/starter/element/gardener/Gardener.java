package org.gxz.joker.starter.element.gardener;

import org.apache.poi.ss.usermodel.Sheet;
import org.gxz.joker.starter.service.Rule;

import java.util.List;

/**
 *
 * 特殊样式的装饰者
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface Gardener {

    void clip(Sheet sheet, List<Rule> ruleList);

    boolean support(Sheet sheet,List<Rule> ruleList);

}
