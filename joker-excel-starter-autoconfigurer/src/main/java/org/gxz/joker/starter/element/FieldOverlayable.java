package org.gxz.joker.starter.element;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface FieldOverlayable extends Overlayable<FieldHolder> {


    @Override
    default FieldHolder defaultValue() {
        return new FieldHolder().setIgnore(new String[]{}).setInclude(new String[]{});
    }
}
