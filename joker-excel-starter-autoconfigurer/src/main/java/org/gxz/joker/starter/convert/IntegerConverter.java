package org.gxz.joker.starter.convert;

import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class IntegerConverter implements Converter<Integer> {

    @Override
    public String convert(Integer value) {
        return Objects.toString(value, null);
    }

    @Override
    public Integer reconvert(String cellValue, Class<? extends Integer> clazz) {
        if (StringUtils.hasText(cellValue)) {
            return Integer.valueOf(cellValue);
        }
        return null;
    }
}
