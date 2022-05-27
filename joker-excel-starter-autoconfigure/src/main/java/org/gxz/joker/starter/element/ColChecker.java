package org.gxz.joker.starter.element;

import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * 对一列只能设置一次数据校验 如果重复 说明配置错了 抛出异常
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ColChecker {

    private final Set<Integer> indexes = new HashSet<>();

    public void check(int... indexes){
        for (int index : indexes) {
            Assert.isTrue(this.indexes.add(index), "你对一个列定义了多次动态选择");
        }

    }
}
