package org.gxz.joker.starter.expression;


import org.springframework.expression.EvaluationException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ${date| format}
 * 支持时间自动注入
 *
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/10/31 17:55
 */
public class DateExpression extends JokerArgumentExpression {

    private static final Map<String, DateTimeFormatter> DATE_TIME_FORMATTER_MAP = new ConcurrentHashMap<>();

    private static final String DEFAULT_PATTERN = "yyyyMMddHHmm";

    private String pattern;

    static {
        DATE_TIME_FORMATTER_MAP.put(DEFAULT_PATTERN,DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
    }


    public DateExpression(String[] args) {
        super(args);
    }


    @Override
    protected void initArgs(String[] args) {
        if(args.length>0){
            this.pattern = args[0];
            DATE_TIME_FORMATTER_MAP.put(pattern,DateTimeFormatter.ofPattern(pattern));
        }else{
            this.pattern = DEFAULT_PATTERN;
        }
    }


    @Override
    public Object getValue() throws EvaluationException {
        DateTimeFormatter dateTimeFormatter = DATE_TIME_FORMATTER_MAP.get(this.pattern);
        return dateTimeFormatter.format(LocalDateTime.now());
    }

}
