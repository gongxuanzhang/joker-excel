package com.tincery.starter.convert;


/**
 * @author gongxuanzhang
 */
public class StringConverter implements Converter<String> {
    @Override
    public String convert(String value) {
        return value;
    }

    @Override
    public String reconvert(String cellValue, Class<? extends String> clazz) throws Exception {
        return cellValue;
    }


}
