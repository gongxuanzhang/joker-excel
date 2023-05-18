package org.gxz.joker.starter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gxz.joker.starter.component.BaseUploadCheck;
import org.gxz.joker.starter.config.ReadConfig;
import org.gxz.joker.starter.config.build.JokerConfigurationDelegate;

import java.util.Collections;
import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class UploadExcelReader<T> extends AbstractExcelReader<T> {


    public UploadExcelReader(Class<T> beanType, ReadConfig readConfig, ObjectMapper objectMapper) {
        super(beanType, readConfig, objectMapper);
    }


    @Override
    protected List<BaseUploadCheck> getUploadChecks() {
        BaseUploadCheck baseUploadCheck = JokerConfigurationDelegate.getUploadChecker(getReadConfig().getCheckValue());
        if (baseUploadCheck == null) {
            return null;
        }
        return Collections.singletonList(baseUploadCheck);
    }


}
