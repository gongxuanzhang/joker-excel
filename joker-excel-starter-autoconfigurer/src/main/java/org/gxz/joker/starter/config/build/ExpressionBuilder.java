package org.gxz.joker.starter.config.build;

import lombok.Getter;
import org.gxz.joker.starter.expression.JokerExpressionParser;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 表达式配置
 * @author gongxuanzhang
 */
@Getter
public class ExpressionBuilder extends JoinAbleBuilder {

    private List<JokerExpressionParser> parserList = new LinkedList<>();

    public ExpressionBuilder(JokerBuilder jokerBuilder) {
        super(jokerBuilder);
    }

    public ExpressionBuilder addParser(JokerExpressionParser parser) {
        parserList.add(parser);
        return this;
    }

    public ExpressionBuilder addParser(JokerExpressionParser... parser) {
        parserList.addAll(Arrays.asList(parser));
        return this;
    }

    public ExpressionBuilder addParser(Collection<JokerExpressionParser> parser) {
        parserList.addAll(parser);
        return this;
    }


}
