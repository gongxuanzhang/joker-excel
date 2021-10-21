package org.gxz.joker.starter.tool;

import org.gxz.joker.starter.element.Overlayable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class FuseUtils {

    private FuseUtils() {
        throw new IllegalStateException("not support construct");
    }


    /**
     * 合并融合
     *
     * @param elements 可以被覆盖的元素
     * @return 融合之后的内容
     **/
    public static <R, T extends Overlayable<R>> R fuse(T... elements) {
        if (elements == null || elements.length == 0) {
            throw new NullPointerException("element must not null");
        }
        Arrays.sort(elements, Comparator.comparing(T::getOrder));
        R result = elements[0].getValue();
        R defaultValue = elements[0].defaultValue();
        if (!Objects.equals(result, defaultValue)) {
            return result;
        }
        for (int i = 1; i < elements.length; i++) {
            if (!Objects.equals(elements[i].getValue(), defaultValue)) {
                return elements[i].getValue();
            }
        }
        return result;
    }
}
