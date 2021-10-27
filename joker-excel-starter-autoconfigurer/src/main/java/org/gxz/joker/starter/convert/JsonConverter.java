package org.gxz.joker.starter.convert;

import com.alibaba.fastjson.JSONObject;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JsonConverter implements Converter<Object> {

    @Override
    public String convert(Object value) {
        if (value == null) {
            return "";
        }
        return JSONObject.toJSONString(value);
    }

    @Override
    public Object reconvert(String cellValue, Class<? extends Object> clazz) {
        if (cellValue.isEmpty()) {
            return null;
        }
        return JSONObject.parseObject(cellValue, clazz);
    }
}
