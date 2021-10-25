package org.gxz.joker.starter.config;


/**
 * Joker配置建造者，
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerBuilder {
    JokerUploadBuilder jokerUploadBuilder;

    JokerExportBuilder jokerExportBuilder;


    public JokerUploadBuilder upload() {
        if (this.jokerUploadBuilder == null) {
            this.jokerUploadBuilder = new JokerUploadBuilder();
        }
        return this.jokerUploadBuilder;
    }

    public JokerExportBuilder export() {
        if (this.jokerExportBuilder == null) {
            this.jokerExportBuilder = new JokerExportBuilder();
        }
        return this.jokerExportBuilder;
    }


    public static class JokerUploadBuilder {

        UploadErrorRowCallBack errorCallBack;
        UploadFinishCallBack finishCallBack;
        UploadSuccessCallBack successCallBack;

        public JokerUploadBuilder errorCallBack(UploadErrorRowCallBack callBack) {
            this.errorCallBack = callBack;
            return this;
        }

        public JokerUploadBuilder finishCallBack(UploadFinishCallBack callBack) {
            this.finishCallBack = callBack;
            return this;
        }

        public JokerUploadBuilder successCallBack(UploadSuccessCallBack callBack) {
            this.successCallBack = callBack;
            return this;
        }


    }

    public static class JokerExportBuilder {

    }


}
