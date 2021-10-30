package org.gxz.joker.starter.element.check;

import org.gxz.joker.starter.exception.CellValueException;
import org.gxz.joker.starter.service.Rule;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface RuleCheck {

    void check(Rule rule,Object value) throws CellValueException;
}
