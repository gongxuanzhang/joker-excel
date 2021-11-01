package org.gxz.joker.starter.expression;



/**
 *
 * 自增表达式解析器
 * 方法隔离
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/10/30 21:20
 *
 */
public class AutoJokerExpressionParser extends MethodIsolationExpressionParser {


    @Override
    protected JokerArgumentExpression isolationResolve(String[] args) {
        return new AutoExpression(args);
    }

    @Override
    public String getMain() {
        return "auto";
    }


}
