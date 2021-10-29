package org.gxz.joker.starter.element.check;

import org.gxz.joker.starter.exception.CellValueException;
import org.gxz.joker.starter.tool.Rule;
import org.springframework.util.StringUtils;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class RequireCheck implements RuleCheck{
    @Override
    public void check(Rule rule, Object value) throws CellValueException {
        if (rule.isRequire() && isNull(value)) {
            throw new CellValueException(rule.getCellName()+"为空");
        }
    }

    public boolean isNull(Object value){
        return value == null || StringUtils.isEmpty(value.toString());
    }
}
