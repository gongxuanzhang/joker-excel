package com.gxz.jokerexceltest.controller.expression;

import org.gxz.joker.starter.expression.JokerArgumentExpression;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class MyExpression extends JokerArgumentExpression {


    public MyExpression(String[] args) {
        super(args);
    }

    @Override
    public String getStrValue() {
        String[] args = getArgs();
        return Integer.parseInt(args[0]) + Integer.parseInt(args[1]) + "";
    }

}
