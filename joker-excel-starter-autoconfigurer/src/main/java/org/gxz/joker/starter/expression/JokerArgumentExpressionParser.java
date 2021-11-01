package org.gxz.joker.starter.expression;


import javax.validation.constraints.NotNull;

/**
 * 抽象的 解析参数的表达式模板
 * 为子类提供表达式参数
 * 例:
 * ${aaa|bbb|ccc}   如果你的getMain方法是aaa  那么此表达式可以被实现解析， args = ["bbb","ccc"]
 *
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/10/30 21:00
 */
public abstract class JokerArgumentExpressionParser implements JokerExpressionParser {


    /**
     * 对参数进行解析
     *
     * @param args 如果参数为空 将会给一个空数组
     * @return 返回真正表达式
     */
    public abstract JokerArgumentExpression resolve(@NotNull String[] args);

    @Override
    public JokerArgumentExpression parseExpression(String expression) {
        String[] args = resolveArgs(expression);
        return resolve(args);
    }

    /**
     * 获取解析的第一个标志
     *
     * @return
     */
    public abstract String getMain();

    @Override
    public boolean support(@NotNull String expression) {
        return expression.startsWith(getMain());
    }

    protected String[] resolveArgs(String expression) {
        String[] split = expression.split(getSeparator());
        if (split.length <= 1) {
            return new String[0];
        }
        String[] result = new String[split.length - 1];
        System.arraycopy(split, 1, result, 0, result.length);
        return result;
    }

    protected String getSeparator() {
        return "\\|";
    }


}
