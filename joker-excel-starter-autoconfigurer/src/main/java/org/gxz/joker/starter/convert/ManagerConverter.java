package org.gxz.joker.starter.convert;


import org.gxz.joker.starter.exception.ConvertException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gongxuanzhang
 * 此解析器为数据提供默认解析器
 * 如果是自定义对象 将使用json解析
 */
public class ManagerConverter implements Converter<Object> {


    private final Map<Class<?>, Converter<?>> converterCache = new HashMap<>();

    private final JsonConverter jsonConverter = new JsonConverter();

    {
        LongConverter longConverter = new LongConverter();
        DoubleConverter doubleConverter = new DoubleConverter();
        BooleanConverter booleanConverter = new BooleanConverter();
        IntegerConverter integerConverter = new IntegerConverter();
        LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
        converterCache.put(Boolean.class, booleanConverter);
        converterCache.put(String.class, new StringConverter());
        converterCache.put(boolean.class, booleanConverter);
        converterCache.put(Integer.class, integerConverter);
        converterCache.put(int.class, integerConverter);
        converterCache.put(Long.class, longConverter);
        converterCache.put(long.class, longConverter);
        converterCache.put(Double.class, doubleConverter);
        converterCache.put(double.class, doubleConverter);
        converterCache.put(Instant.class, new InstantConverter(localDateTimeConverter));
        converterCache.put(LocalDateTime.class, new LocalDateTimeConverter());
    }

    @Override
    public String convert(Object value) throws ConvertException {
        Class<?> clazz = value == null ? String.class : value.getClass();
        Converter orDefault = converterCache.getOrDefault(clazz, jsonConverter);
        return orDefault.convert(value);
    }


    @Override
    public Object reconvert(String cellValue, Class<Object> clazz) throws ConvertException {
        Converter orDefault = converterCache.getOrDefault(clazz, jsonConverter);
        return orDefault.reconvert(cellValue, clazz);
    }
}
