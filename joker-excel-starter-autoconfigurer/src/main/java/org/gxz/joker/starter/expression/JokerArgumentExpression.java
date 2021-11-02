package org.gxz.joker.starter.expression;

import org.gxz.joker.starter.tool.ThreadMethodHolder;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.TypedValue;
import org.springframework.expression.common.ExpressionUtils;
import org.springframework.expression.spel.standard.SpelExpression;


/**
 * 抽象出的JokerExpression
 * 继承SpelExpression是为了在SpelExpressionParser中可以支持返回子类，
 * 大量方法不能使用，对外只提供getValue()方法，但是不影响功能,目前JokerExpression只支持解析excel相关内容，不作为注入使用。
 * <p>
 * 一般来说jokerExpression解析之前，表达式整体会被spel解析过一次，所以不会出现jokerExpression表达式解析覆盖了spel的情况
 * 默认的表达式解析是解析${main|arg1|arg2|..}  最终由表达式本体进行结果计算
 * <p>
 * 表达式是方法隔离的，每个导出请求的方法会独立存储一个单独的表达式，比如自增表达式：  不会出现两个方法共享了一个变量的情况
 * <p>
 * 所以暂时只支持String的解析。  当然如果想类型转换也可以使用${@link JokerArgumentExpression#getValue(Class)} 但尽量还是不要用
 * <p>
 * 子类可以通过${@link ThreadMethodHolder#getCurrentMethod()}方法获得执行你当前解析的方法签名，以达到方法隔离
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class JokerArgumentExpression extends SpelExpression {

    private String[] args;

    protected String[] getArgs(){
        return this.args;
    }


    public JokerArgumentExpression(String[] args) {
        super(null, null, null);
        this.args = args;
        initArgs(args);
    }



    /**
     * 通过表达式参数，为自己初始化
     */
    protected void initArgs(String[] args) {

    }

    public abstract String getStrValue();

    @Override
    public Object getValue() throws EvaluationException{
        return getStrValue();
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
