package org.gxz.joker.starter.element.check;

import org.gxz.joker.starter.config.ExcelFieldDescription;
import org.gxz.joker.starter.exception.CheckValueException;
import org.gxz.joker.starter.service.ColumnRule;
import org.gxz.joker.starter.service.DataRule;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class RequireCheck implements RuleCheck {


    @Override
    public void check(ColumnRule rule, Object value) throws CheckValueException {
        boolean require =
                Optional.ofNullable(rule.getDataRule())
                        .map(DataRule::getExcelFieldDescription)
                        .map(ExcelFieldDescription::isRequire)
                        .orElse(false);
        if (require && isNull(value)) {
            String headHead = Optional.of(rule.getDataRule().getExcelFieldDescription().getHeadName()).orElse(null);
            if(!StringUtils.hasText(headHead)){
                headHead = rule.getDataRule().getFieldName();
            }
            throw new CheckValueException(headHead + "为空");
        }
    }

    public boolean isNull(Object value) {
        return value == null || StringUtils.isEmpty(value.toString());
    }
}
