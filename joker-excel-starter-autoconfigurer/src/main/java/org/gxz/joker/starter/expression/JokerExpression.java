package org.gxz.joker.starter.expression;

import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.ast.SpelNodeImpl;
import org.springframework.expression.spel.standard.SpelExpression;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerExpression extends SpelExpression {

    public JokerExpression(String expression, SpelNodeImpl ast, SpelParserConfiguration configuration) {
        super(expression, ast, configuration);
    }
}
