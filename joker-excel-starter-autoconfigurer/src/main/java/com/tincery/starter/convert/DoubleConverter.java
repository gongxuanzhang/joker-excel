package com.tincery.starter.convert;

import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class DoubleConverter implements Converter<Double> {

    @Override
    public String convert(Double value) {
        return Objects.toString(value,null);
    }

    @Override
    public Double reconvert(String cellValue, Class<? extends Double> clazz) throws Exception {
        if(StringUtils.hasText(cellValue)){
            return Double.valueOf(cellValue);
        }
        return null;
    }
}
