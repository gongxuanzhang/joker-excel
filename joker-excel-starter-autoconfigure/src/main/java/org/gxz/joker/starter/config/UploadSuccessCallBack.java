package org.gxz.joker.starter.config;

import java.util.List;

/**
 * 当上传解析成功之后的回调函数，
 * 如果解析过程中出现问题，将不会触发此回调函数，
 * 如果你想在未完成的时候触发回调函数，请使用${@link UploadFinishCallBack}
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@FunctionalInterface
public interface UploadSuccessCallBack {


    /**
     * 解析成功之后的数据
     **/
    void onSuccess(List<?> data);
}
