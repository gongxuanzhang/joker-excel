package org.gxz.joker.starter.expression;


/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class DateExpressionParser extends JokerArgumentExpressionParser {
    @Override
    public JokerArgumentExpression resolve(String[] args) {
        return new DateExpression(args);
    }

    @Override
    public String getMain() {
        return "date";
    }
}
