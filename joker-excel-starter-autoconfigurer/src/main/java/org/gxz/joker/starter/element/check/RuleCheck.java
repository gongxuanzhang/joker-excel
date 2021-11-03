package org.gxz.joker.starter.element.check;

import org.gxz.joker.starter.exception.CheckValueException;
import org.gxz.joker.starter.service.ColumnRule;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface RuleCheck {

    /**
     * 校验内容，如果校验失败直接抛出异常
     *
     * @param rule  整列的规则
     * @param value 单元格值
     **/
    void check(ColumnRule rule, Object value) throws CheckValueException;
}
