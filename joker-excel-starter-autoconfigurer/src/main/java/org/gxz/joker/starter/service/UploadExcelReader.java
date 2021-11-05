package org.gxz.joker.starter.service;

import org.gxz.joker.starter.annotation.Upload;
import org.gxz.joker.starter.component.BaseUploadCheck;
import org.gxz.joker.starter.config.build.JokerConfigurationDelegate;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class UploadExcelReader<T> extends AbstractExcelReader<T> {

    private final Upload upload;

    public UploadExcelReader(Class<T> beanType, Upload upload) {
        super(beanType);
        Assert.notNull(upload, "无法解析upload");
        this.upload = upload;
    }


    @Override
    protected List<BaseUploadCheck> getUploadChecks() {
        BaseUploadCheck baseUploadCheck = JokerConfigurationDelegate.getUploadChecker(upload.value());
        if (baseUploadCheck == null) {
            return null;
        }
        return Collections.singletonList(baseUploadCheck);
    }


}
