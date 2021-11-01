package org.gxz.joker.starter.expression;

import javax.validation.constraints.NotNull;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class DateExpressionParser extends JokerArgumentExpressionParser {
    @Override
    public JokerArgumentExpression resolve(@NotNull String[] args) {
        return new DateExpression(args);
    }

    @Override
    public String getMain() {
        return "date";
    }
}
