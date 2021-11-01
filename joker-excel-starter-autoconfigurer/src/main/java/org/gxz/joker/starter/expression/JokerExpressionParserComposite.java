package org.gxz.joker.starter.expression;

import org.gxz.joker.starter.config.build.JokerConfigurationDelegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 表达式解析器的组合，所有解析器最终的容器，
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerExpressionParserComposite implements JokerExpressionParser {


    private Map<String, JokerExpressionParser> cache = new ConcurrentHashMap<>();

    private List<JokerExpressionParser> parserList = new ArrayList<>();

    {
        parserList.add(new AutoJokerExpressionParser());
        parserList.addAll(JokerConfigurationDelegate.getParserList());
    }

    public void addJokerExpressionParser(JokerExpressionParser parser) {
        this.parserList.add(parser);
    }

    @Override
    public boolean support(String expressionStr) {
        for (JokerExpressionParser jokerExpressionParser : parserList) {
            if (jokerExpressionParser.support(expressionStr)) {
                cache.put(expressionStr, jokerExpressionParser);
                return true;
            }
        }
        return false;
    }


    @Override
    public JokerArgumentExpression parseExpression(String expressionStr) {
        JokerExpressionParser jokerExpressionParser = cache.get(expressionStr);
        return jokerExpressionParser.parseExpression(expressionStr);
    }
}
