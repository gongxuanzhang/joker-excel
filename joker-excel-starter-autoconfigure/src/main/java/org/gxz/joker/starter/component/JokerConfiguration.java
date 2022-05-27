package org.gxz.joker.starter.component;

import org.gxz.joker.starter.element.DefaultValueConstant;
import org.gxz.joker.starter.service.ExcelNameFactory;

/**
 * Joker导出的配置类
 * 此类可以配置导出相关事宜 包括包含字段 忽略字段  导出内容，
 * 优先级  配置类> export注解属性> excelData注解属性
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface JokerConfiguration {


    /**
     * 当配置类的属性为null 或者为默认值的时候，是否会被其他低优先级的属性覆盖
     **/
    default boolean overField() {
        return false;
    }

    /**
     * @return 返回配置名字
     **/
    String getName();

    /**
     * @return 返回导出的excel的名字
     **/
    default String excelName() {
        return "excel.xlsx";
    }

    /**
     * @return 命名工厂
     **/
    default Class<? extends ExcelNameFactory> nameFactory() {
        return DefaultValueConstant.DEFAULT_SUPPORT_CLASS;
    }

    /**
     * @return 返回生成的sheet的名字
     **/
    default String sheetName() {
        return "sheet";
    }

    /**
     * @return 返回可以忽略的字段名称
     **/
    default String[] ignore() {
        return new String[]{};
    }


    /**
     * @return 返回要包含的字段名称
     **/
    default String[] include() {
        return new String[]{};
    }


}
