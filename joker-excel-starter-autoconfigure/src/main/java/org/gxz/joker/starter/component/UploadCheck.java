package org.gxz.joker.starter.component;


import org.gxz.joker.starter.exception.ConvertException;

import java.util.List;
import java.util.function.Predicate;

/**
 * 解析成功之后的 切面
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface UploadCheck {


    /**
     * 唯一标识
     **/
    String getId();

    /**
     * 校验
     **/
    void uploadCheck(Object data) throws ConvertException;

    /**
     * 拿到上下文数据
     **/
    List<?> getContent();


    default Predicate<Object> predicate() {
        return (d) -> {
            try {
                uploadCheck(d);
                return false;
            } catch (Exception e) {
                return true;
            }
        };
    }


}
