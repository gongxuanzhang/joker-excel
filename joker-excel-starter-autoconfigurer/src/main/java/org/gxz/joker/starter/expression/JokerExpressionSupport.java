package org.gxz.joker.starter.expression;

import org.gxz.joker.starter.exception.JokerRuntimeException;
import org.gxz.joker.starter.tool.ThreadMethodHolder;


/**
 * 在Spring中有Spel表达式
 * 此接口定义了可以解析Spel之外的规则，
 * 可以解析自定义的规则。
 * 因为导出excel的方法不同，如果你想把实现的功能在方法级别隔离, 可以使用${@link ThreadMethodHolder#getCurrentMethod()}来获取当前调用的方法
 *
 * @author gxz gongxuanzhang@foxmail.com
 */
public interface JokerExpressionSupport {


    /**
     * @throws JokerRuntimeException 如果参数不符合你的预期，或者无法解析，请直接抛出异常
     * @return 解析返回的表达式
     */
    JokerArgumentExpression resolve(String expression);


    /**
     * 是否支持表达式
     * 这个表达式一般来说是spel表达式所无法解析的  接口的子类将进行接下来的解析
     * @param expression 特殊的被解析过的表达式 ${XXX|BBB}   expression = XXX|BBB
     * @return 是否支持表达式
     */
     boolean support(String expression);


}
