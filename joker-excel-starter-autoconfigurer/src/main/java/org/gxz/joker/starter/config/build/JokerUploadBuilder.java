package org.gxz.joker.starter.config.build;

import lombok.Getter;
import org.gxz.joker.starter.config.UploadErrorRowCallBack;
import org.gxz.joker.starter.config.UploadFinishCallBack;
import org.gxz.joker.starter.config.UploadSuccessCallBack;

/**
 * @author gongxuanzhang
 */
@Getter
public class JokerUploadBuilder extends JoinAbleBuilder {

    UploadErrorRowCallBack errorCallBack;
    UploadFinishCallBack finishCallBack;
    UploadSuccessCallBack successCallBack;

    public JokerUploadBuilder(JokerBuilder jokerBuilder) {
        super(jokerBuilder);
    }

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
