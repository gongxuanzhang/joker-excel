package org.gxz.joker.starter.convert;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
public class JsonConverter implements Converter<Object> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(Object value) {
        if (value == null) {
            return "";
        }
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public Object reconvert(String cellValue, Class<Object> clazz) {
        if (cellValue.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(cellValue, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
