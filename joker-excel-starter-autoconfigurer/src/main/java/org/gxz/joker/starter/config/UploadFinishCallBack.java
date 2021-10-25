package org.gxz.joker.starter.config;

import java.util.List;

/**
 *
 * 当上传excel解析完成之后的回调函数
 * 此事件回调不保证解析的完成，过程中可能有异常抛出
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@FunctionalInterface
public interface UploadFinishCallBack {

    /**
     *
     *
     * @param data 解析完成的数据,异常内容会直接忽略掉
     *
     **/
    void onFinish(List<?> data);

}
