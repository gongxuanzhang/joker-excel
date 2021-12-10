package com.gxz.jokerexceltest.controller.config;

import com.gxz.jokerexceltest.controller.User;
import org.gxz.joker.starter.component.BaseUploadCheck;
import org.gxz.joker.starter.exception.ConvertException;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class MyCheck extends BaseUploadCheck {


    @Override
    public String getId() {
        return "123";
    }

    @Override
    public void uploadCheck(Object data) throws ConvertException {
        if (((User) data).getSex().contains("男")) {
            System.out.println(1);
            throw new ConvertException("错了");
        }
    }
}
