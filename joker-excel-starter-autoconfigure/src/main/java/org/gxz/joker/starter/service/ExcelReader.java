package org.gxz.joker.starter.service;


import org.gxz.joker.starter.component.ReadHolder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface ExcelReader<T> {

    /**
     * 通过一个流 读取相关信息
     * @throws IOException 读取异常 io异常
     * @param inputStream 输入流
     * @return 返回信息整合
     **/
    ReadHolder<T> read(InputStream inputStream) throws IOException;
}
