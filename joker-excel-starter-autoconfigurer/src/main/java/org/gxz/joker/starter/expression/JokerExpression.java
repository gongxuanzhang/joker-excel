package org.gxz.joker.starter.expression;

import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.ast.SpelNodeImpl;
import org.springframework.expression.spel.standard.SpelExpression;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerExpression extends SpelExpression {



    /**
     * Construct an expression, only used by the parser.
     *
     * @param expression
     * @param ast
     * @param configuration
     */
    public JokerExpression(String expression, SpelNodeImpl ast, SpelParserConfiguration configuration) {
        super(expression, ast, configuration);
    }
}
