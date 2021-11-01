package org.gxz.joker.starter.element;

import org.gxz.joker.starter.exception.ConvertException;

import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface Checkable<T> {

    void check() throws ConvertException;

    default void check(List<T> data) {

    }

}
