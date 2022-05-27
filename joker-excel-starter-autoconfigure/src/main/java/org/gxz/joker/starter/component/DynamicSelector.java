package org.gxz.joker.starter.component;

import org.gxz.joker.starter.element.FieldInfo;

/**
 *
 * 动态下拉框接口内容
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface DynamicSelector {

    /**
     * 具体配置的动态信息
     * @param info 列信息
     * @return 动态生成的内容
     **/
    DynamicReport dynamic(FieldInfo[] info);

}
