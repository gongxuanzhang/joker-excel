package org.gxz.joker.starter.expression;


import org.gxz.joker.starter.exception.JokerRuntimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gxz gongxuanzhang@foxmail.com
 *
 *
 *  * 假如  @Value("${aaaa|bbbb|cccc}")  在spring上下文中无法解析，你可以自定义解析逻辑增强spel
 *  * 例如上文，最终本类的resolve方法中会加入一个["bbbb","cccc"] 而aaaa作为入口(main)
 *  * <p>
 *
 * @date 2021/10/30 20:58
 */
public class JokerExpressionSupportComposite implements JokerExpressionSupport{

    private List<JokerArgumentExpressionSupport> supportList = new ArrayList<>();


    private Map<String, JokerArgumentExpressionSupport> cache = new ConcurrentHashMap<>();

    public void addSupport(JokerArgumentExpressionSupport argumentsJokerExpressionSupport){
        supportList.add(argumentsJokerExpressionSupport);
    }

    @Override
    public JokerArgumentExpression resolve(String[] arg) throws JokerRuntimeException {
            return
    }

    @Override
    public JokerArgumentExpression resolve(String expression) {
        return null;
    }

    @Override
    public String getMain() {
        return "";
    }

    @Override
    public boolean support(String expression) {
        for (JokerArgumentExpressionSupport support : supportList) {
            if(support.support(expression)){
                cache.put(expression,support);
                return true;
            }
        }
        return false;
    }
}
