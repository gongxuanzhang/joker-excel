package org.gxz.joker.starter.config.build;

import java.util.function.Predicate;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface ValueChainStyleBuilder extends StyleBuilder{


    /**
     *
     * 支持链式编程，一般来说逐级传递
     * 创建一个新的ValueStyleBuilder
     * @param valuePredicate value命中规则
     * @return 返回内容由子类定义意义
     **/
    ValueStyleBuilder whenValue(Predicate<String> valuePredicate);

}
