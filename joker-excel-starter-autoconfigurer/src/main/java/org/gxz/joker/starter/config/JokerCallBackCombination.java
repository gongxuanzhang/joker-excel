package org.gxz.joker.starter.config;


import org.apache.poi.ss.usermodel.Row;
import org.gxz.joker.starter.config.build.JokerBuilder;
import org.gxz.joker.starter.config.build.JokerExportBuilder;
import org.gxz.joker.starter.config.build.JokerUploadBuilder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerCallBackCombination {

    private JokerCallBackCombination() {

    }

    static List<UploadErrorRowCallBack> errorCallBack;
    static List<UploadFinishCallBack> finishCallBack;
    static List<UploadSuccessCallBack> successCallBack;


    public static void registerBuild(JokerBuilder jokerBuilder) {
        JokerUploadBuilder jokerUploadBuilder = jokerBuilder.getJokerUploadBuilder();
        JokerExportBuilder jokerExportBuilder = jokerBuilder.getJokerExportBuilder();
        register(jokerUploadBuilder.getErrorCallBack());
        register(jokerUploadBuilder.getFinishCallBack());
        register(jokerUploadBuilder.getSuccessCallBack());
    }

    private static void register(UploadErrorRowCallBack callBack) {
        if (errorCallBack == null) {
            errorCallBack = new ArrayList<>();
        }
        errorCallBack.add(callBack);
    }

    private static void register(UploadFinishCallBack callBack) {
        if (finishCallBack == null) {
            finishCallBack = new ArrayList<>();
        }
        finishCallBack.add(callBack);
    }

    private static void register(UploadSuccessCallBack callBack) {
        if (successCallBack == null) {
            successCallBack = new ArrayList<>();
        }
        successCallBack.add(callBack);
    }

    public static void uploadRowError(Row row, Throwable t) {
        if (!CollectionUtils.isEmpty(errorCallBack)) {
            for (UploadErrorRowCallBack callBack : errorCallBack) {
                callBack.onRowError(row, t);
            }
        }
    }

    public static void uploadFinish(List<?> data) {
        if (!CollectionUtils.isEmpty(finishCallBack)) {
            for (UploadFinishCallBack callBack : finishCallBack) {
                callBack.onFinish(data);
            }
        }
    }

    public static void uploadSuccess(List<?> data) {
        if (!CollectionUtils.isEmpty(successCallBack)) {
            for (UploadSuccessCallBack callBack : successCallBack) {
                callBack.onSuccess(data);
            }
        }
    }

}
