package com.gxz.jokerexceltest.controller.expression;

import org.gxz.joker.starter.expression.JokerArgumentExpression;
import org.gxz.joker.starter.expression.JokerArgumentExpressionParser;

import javax.validation.constraints.NotNull;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class MyExpressionParser extends JokerArgumentExpressionParser {


    @Override
    public JokerArgumentExpression resolve(@NotNull String[] args) {
        return new MyExpression(args);
    }

    @Override
    public String getMain() {
        return "my";
    }
}
