package org.gxz.joker.starter.expression;

/**
 *
 * jokerExpression 的解析器， 不遵守Spel的的parser 由${@link JokerExpressionParserAdapter}适配
 *
 *
 *
 * @see JokerExpressionParserAdapter
 * @see JokerArgumentExpression
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface JokerExpressionParser {


    /**
     *
     * 子类是否支持表达式解析
     * @param expressionStr 是否支持表达式解析
     * @return 支持或者不支持
     **/
    boolean support(String expressionStr);


    /**
     *
     * 解析表达式
     * @param expressionStr 通过了support()方法的表达式。
     * @return 返回解析之后的表达式
     **/
    JokerArgumentExpression parseExpression(String expressionStr);

}
