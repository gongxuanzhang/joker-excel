package com.gxz.jokerexceltest.controller.config;

import com.gxz.jokerexceltest.controller.User;
import org.gxz.joker.starter.component.BaseUploadCheck;
import org.gxz.joker.starter.component.ExportAspect;
import org.gxz.joker.starter.exception.ConvertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class MyCheck extends BaseUploadCheck {

    @Autowired
    private ExportAspect jokerAutoConfiguration;

    @Override
    public String getId() {
        return "123";
    }

    @Override
    public void uploadCheck(Object data) throws ConvertException {
        if(((User)data).getSex().contains("男")){
            System.out.println(1);
            throw new ConvertException("错了");
        }
    }
}