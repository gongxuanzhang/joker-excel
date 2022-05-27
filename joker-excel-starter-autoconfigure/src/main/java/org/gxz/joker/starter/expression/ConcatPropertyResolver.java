package org.gxz.joker.starter.expression;


import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;

/**
 * 为了解决一个resolver不能完全解析表达式的问题
 * 主解析器先解析一次
 * 副解析器再解析一次
 * 最终再交给主表达式
 * 达到可以动态参数和定制化需求的整合
 * 例：    定制化需求：   user = zhangsan
 * spring 参数: zhangsan.age = 14
 * 最终可以解析表达式${${user}.age} 结果为14
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ConcatPropertyResolver {

    private final ConfigurablePropertyResolver main;

    private final ExpressionParser[] concat;

    private ParserContext parserContext = new ParserContext() {
        @Override
        public boolean isTemplate() {
            return true;
        }

        @Override
        public String getExpressionPrefix() {
            return "${";
        }

        @Override
        public String getExpressionSuffix() {
            return "}";
        }
    };

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
            Expression expression = parser.parseExpression(value, parserContext);
            value = expression.getValue(String.class);
        }
        return main.resolvePlaceholders(value);
    }


}
