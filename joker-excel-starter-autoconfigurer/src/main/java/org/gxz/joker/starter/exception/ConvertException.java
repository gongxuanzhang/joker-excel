package org.gxz.joker.starter.exception;


import java.io.IOException;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 抛出这个异常说明在excel convert解析的过程中出现了问题
 **/
public class ConvertException extends IOException {


    public ConvertException(String message) {
        super(message);
    }


}
