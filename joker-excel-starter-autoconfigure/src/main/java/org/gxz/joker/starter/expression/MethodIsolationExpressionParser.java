package org.gxz.joker.starter.expression;

import org.gxz.joker.starter.tool.ThreadMethodHolder;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 方法隔离的表达式解析器
 * 包装了方法缓存
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class MethodIsolationExpressionParser extends JokerArgumentExpressionParser {

    private Map<Method, JokerArgumentExpression> cache = new ConcurrentHashMap<>();

    @Override
    public JokerArgumentExpression resolve(@NotNull String[] args) {
        Method currentMethod = ThreadMethodHolder.getCurrentMethod();
        return cache.computeIfAbsent(currentMethod, k -> isolationResolve(args));
    }


    /**
     * 方法隔离的解析
     *
     * @param args 参数
     * @return 解析的表达式
     **/
    protected abstract JokerArgumentExpression isolationResolve(String[] args);

}
