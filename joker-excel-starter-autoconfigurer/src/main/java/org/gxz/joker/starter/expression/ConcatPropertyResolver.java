package org.gxz.joker.starter.expression;


import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;

/**
 * 为了解决一个resolver不能完全解析表达式的问题
 * 可以通过多个resolver按序解析自己所能解析的表达式之后
 * 最终统一
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ConcatPropertyResolver {

    private final ConfigurablePropertyResolver main;

    ExpressionParser[] concat;

    public ConcatPropertyResolver(ConfigurablePropertyResolver main, ExpressionParser... concat) {
        this.main = main;
        this.concat = concat;
    }

    public String resolvePlaceholders(String text) {
        String value = main.resolvePlaceholders(text);
        if (concat == null || concat.length == 0) {
            return value;
        }
        for (ExpressionParser parser : concat) {
            Expression expression = parser.parseExpression(value);
            value = expression.getValue(String.class);
        }
        return main.resolvePlaceholders(value);
    }


}
