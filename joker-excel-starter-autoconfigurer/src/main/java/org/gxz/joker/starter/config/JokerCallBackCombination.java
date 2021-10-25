package org.gxz.joker.starter.config;


import java.util.ArrayList;
import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerCallBackCombination {

    static List<UploadErrorRowCallBack> errorCallBack;
    static List<UploadFinishCallBack> finishCallBack;
    static List<UploadSuccessCallBack> successCallBack;



    public static void registerBuild(JokerBuilder jokerBuilder) {
        JokerBuilder.JokerUploadBuilder jokerUploadBuilder = jokerBuilder.jokerUploadBuilder;
        JokerBuilder.JokerExportBuilder jokerExportBuilder = jokerBuilder.jokerExportBuilder;
        register(jokerUploadBuilder.errorCallBack);
        register(jokerUploadBuilder.finishCallBack);
        register(jokerUploadBuilder.successCallBack);
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


}
