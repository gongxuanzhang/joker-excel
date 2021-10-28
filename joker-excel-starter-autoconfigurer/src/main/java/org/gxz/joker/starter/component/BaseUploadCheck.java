package org.gxz.joker.starter.component;

import org.apache.commons.collections4.list.UnmodifiableList;

import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class BaseUploadCheck implements UploadCheck{

    private List<?> prototype;

    @Override
    public List<?> getContent() {
        return new UnmodifiableList<>(prototype);
    }

    public void register(List<?> prototype){
        this.prototype = prototype;
    }
}
