package com.tincery.starter.convert;

import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class LongConverter implements Converter<Long> {

    @Override
    public String convert(Long value) {
        return Objects.toString(value, null);
    }

    @Override
    public Long reconvert(String cellValue, Class<? extends Long> clazz) throws Exception {
        if (StringUtils.hasText(cellValue)) {
            return Long.valueOf(cellValue);
        }
        return null;
    }
}
