package org.gxz.joker.starter.element;

import org.gxz.joker.starter.exception.CheckValueException;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface Checkable {

    /**
     * 自我校验，如果有问题直接抛出异常
     *
     * @throws CheckValueException 校验失败直接抛出异常
     **/
    void check() throws CheckValueException;


}
