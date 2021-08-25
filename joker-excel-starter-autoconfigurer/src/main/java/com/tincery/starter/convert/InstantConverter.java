package com.tincery.starter.convert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class InstantConverter implements Converter<Instant> {

    final LocalDateTimeConverter delegate;

    public InstantConverter(LocalDateTimeConverter delegate) {
        this.delegate = delegate;
    }

    @Override
    public String convert(Instant value) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(value, ZoneId.systemDefault());
        return delegate.convert(localDateTime);
    }

    @Override
    public Instant reconvert(String cellValue, Class<? extends Instant> clazz) throws Exception {
        LocalDateTime reconvert = delegate.reconvert(cellValue, LocalDateTime.class);
        return reconvert.toInstant(ZoneOffset.of("+8"));
    }
}
