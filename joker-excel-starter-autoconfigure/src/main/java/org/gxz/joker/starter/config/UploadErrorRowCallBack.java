package org.gxz.joker.starter.config;

import org.apache.poi.ss.usermodel.Row;


/**
 * 当上传excel解析过程中，行解析失败的时候的回调函数
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@FunctionalInterface
public interface UploadErrorRowCallBack {

    /**
     * @param row       解析错误的行
     * @param throwable 出现的异常
     **/
    void onRowError(Row row, Throwable throwable);

}
