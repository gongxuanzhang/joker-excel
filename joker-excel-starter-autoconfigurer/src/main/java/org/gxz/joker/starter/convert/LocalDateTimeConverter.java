package org.gxz.joker.starter.convert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public String convert(LocalDateTime value) {
        return DATE_TIME_FORMATTER.format(value);
    }

    @Override
    public LocalDateTime reconvert(String cellValue, Class<LocalDateTime> clazz) {
        return LocalDateTime.from(DATE_TIME_FORMATTER.parse(cellValue));
    }
}
