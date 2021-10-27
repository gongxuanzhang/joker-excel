package org.gxz.joker.starter.element;


import java.util.Comparator;

/**
 * 实现这个接口的子类 会获得可以和同样实现此接口的子类合并，
 * 合并之后有优先级，且会覆盖默认值
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface Overlayable<T> extends Comparable<Overlayable<T>> {

    /**
     * 优先级
     *
     * @return 返回数字越大 优先级越小
     **/
    int getOrder();

    /**
     * 默认值，如果没有值，将会用默认值替代
     **/
    T defaultValue();

    /**
     * 拿到此属性的值;
     **/
    T getValue();


    @Override
    default int compareTo(Overlayable<T> o) {
        return Integer.compare(getOrder(), o.getOrder());
    }
}
