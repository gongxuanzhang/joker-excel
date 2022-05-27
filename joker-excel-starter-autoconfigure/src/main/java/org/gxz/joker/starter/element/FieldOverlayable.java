package org.gxz.joker.starter.element;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface FieldOverlayable extends Overlayable<FieldHolder> {


    /**
     *
     * 字段的默认值
     * @see org.gxz.joker.starter.annotation.Export 可以关注此类的解析规则
     * @return 两个都是空值
     **/
    @Override
    default FieldHolder defaultValue() {
        return new FieldHolder().setIgnore(new String[]{}).setInclude(new String[]{});
    }
}
