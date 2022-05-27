package org.gxz.joker.starter.tool;


import java.lang.reflect.Method;

/**
 * 负责线程上下文的传送， 辅助内容
 *
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/10/31 17:08
 */
public class ThreadMethodHolder {

    public static final ThreadLocal<Method> CONTEXT = new ThreadLocal<>();

    public static void setMethod(Method method) {
        CONTEXT.set(method);
    }

    public static void clear() {
        CONTEXT.remove();
    }

    public static Method getCurrentMethod() {
        return CONTEXT.get();
    }


}
