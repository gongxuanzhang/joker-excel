package org.gxz.joker.starter.expression;

import org.springframework.expression.ParseException;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.lang.Nullable;


/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerExpressionParser extends SpelExpressionParser {

    public JokerExpressionSupport jokerExpressionSupport;

    private static ParserContext parserContext = new ParserContext() {
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


    @Override
    protected SpelExpression doParseExpression(String expressionString, @Nullable ParserContext context) throws ParseException {
        if (jokerExpressionSupport.support(expressionString)) {
            String[] args = jokerExpressionSupport.resolveArgs(expressionString);
            return jokerExpressionSupport.resolve(args);
        }
        return super.doParseExpression(expressionString,context);
    }


}
