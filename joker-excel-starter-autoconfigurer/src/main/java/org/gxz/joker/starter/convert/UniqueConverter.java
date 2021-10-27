package org.gxz.joker.starter.convert;

import org.gxz.joker.starter.exception.ConvertException;
import org.gxz.joker.starter.exception.ExcelException;

import java.util.HashSet;
import java.util.Set;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class UniqueConverter<T> implements Converter<T> {

    private final Converter<T> converter;

    private Set<String> exportValues = new HashSet<>();

    private Set<T> uploadValues = new HashSet<>();

    public UniqueConverter(Converter<T> converter) {
        this.converter = converter;
    }


    @Override
    public String convert(T value) throws ConvertException {
        String convert = converter.convert(value);
        if (!exportValues.add(convert)) {
            throw new ConvertException("第r%行 第c%列 [v%] 重复数据");
        }
        return convert;
    }

    @Override
    public T reconvert(String cellValue, Class<? extends T> clazz) throws ConvertException {
        T reconvert = converter.reconvert(cellValue, clazz);
        if (!uploadValues.add(reconvert)) {
            throw new ConvertException("第r%行 第c%列 [v%] 重复数据");
        }
        return reconvert;
    }
}
