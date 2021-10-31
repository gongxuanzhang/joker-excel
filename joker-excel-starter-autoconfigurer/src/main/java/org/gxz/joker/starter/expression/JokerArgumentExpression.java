package org.gxz.joker.starter.expression;

import org.gxz.joker.starter.tool.ThreadMethodHolder;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.TypedValue;
import org.springframework.expression.common.ExpressionUtils;
import org.springframework.expression.spel.standard.SpelExpression;


/**
 * 抽象出的JokerExpression
 * 继承SpelExpression是为了在SpelExpressionParser中可以支持返回子类，
 * 大量方法不能使用，但是不影响解析,目前JokerExpression只支持解析excel相关内容，不作为注入使用。
 * 所以暂时只支持String的解析。  当然如果想类型转换也可以使用${@link JokerArgumentExpression#getValue(Class)} 但尽量还是不要用
 * <p>
 * 子类可以通过${@link ThreadMethodHolder#getCurrentMethod()}方法获得执行你当前解析的方法签名，以达到方法隔离
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class JokerArgumentExpression extends SpelExpression {

    protected String[] args;

    public JokerArgumentExpression(String expression, String[] args) {
        super(expression, null, null);
        initArgs();
    }


    /**
     * 通过表达式参数，为自己初始化
     */
    protected void initArgs(String[] args) {

    }

    @Override
    public Object getValue() throws EvaluationException {

    }


    @Override
    public <T> T getValue(Class<T> expectedResultType) throws EvaluationException {
        Object result = getValue();
        if (expectedResultType == null) {
            return (T) result;
        }
        return ExpressionUtils.convertTypedValue(
                getEvaluationContext(), new TypedValue(result), expectedResultType);
    }


}
