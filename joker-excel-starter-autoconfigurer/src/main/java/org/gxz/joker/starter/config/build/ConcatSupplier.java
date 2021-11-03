package org.gxz.joker.starter.config.build;

import org.gxz.joker.starter.config.ExcelFieldDescription;

/**
 *
 * 定义 可以通过${@link ExcelFieldDescription}定义信息获取内容的接口
 * 比如 获取表头前缀， 获取表头后缀等等
 * @author gxz gongxuanzhang@foxmail.com
 **/
@FunctionalInterface
public interface ConcatSupplier {

    /**
     * 获取值，具体意义由子类定义
     * @param description 参数信息
     * @return 返回个啥
     **/
    String apply(ExcelFieldDescription description);

}
