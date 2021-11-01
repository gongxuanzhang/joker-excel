package org.gxz.joker.starter.expression;

import org.springframework.expression.ParseException;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.lang.Nullable;


/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerExpressionParserAdapter extends SpelExpressionParser {

    private final JokerExpressionParser parser;

    public JokerExpressionParserAdapter(JokerExpressionParser parser) {
        this.parser = parser;
    }


    @Override
    protected SpelExpression doParseExpression(String expressionString, @Nullable ParserContext context) throws ParseException {
        if(parser.support(expressionString)){
            return parser.parseExpression(expressionString);
        }
        return super.doParseExpression(expressionString,context);
    }


}
