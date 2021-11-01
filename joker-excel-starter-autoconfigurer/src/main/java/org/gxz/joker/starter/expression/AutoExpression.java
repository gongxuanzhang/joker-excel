package org.gxz.joker.starter.expression;


import org.springframework.expression.EvaluationException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ${auto}
 * 表示会从1开始无限自增
 * ${auto|4|1d}   两个参数必须同时使用
 * 第一个参数是4   表示最终的index 填充4位    0001 0002  最大9999  如果时间内超过了9999 将会抛出异常
 * 第二个参数表示重置时间  支持 y年 M月 d日  h时 m分 s秒  其中 y、d、h、s不区分大小写
 * 上例表示 1天重置
 *
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/10/31 17:55
 */
public class AutoExpression extends JokerArgumentExpression {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+");

    private AtomicInteger count;

    private AtomicLong timeStamp;

    private int numberFill;

    private int maxCount;

    private long durationNum;

    private boolean enableReset;

    public AutoExpression(String[] args) {
        super(args);
    }


    @Override
    protected void initArgs(String[] args) {
        count = new AtomicInteger(0);
        if (args.length < 2) {
            enableReset = false;
        } else {
            enableReset = true;
            this.numberFill = Integer.parseInt(args[0]);
            this.durationNum = resolveResetUnit(args[1]);
            this.timeStamp = new AtomicLong(Instant.now().toEpochMilli() / durationNum * durationNum);
            this.maxCount = (int) Math.pow(10, numberFill);
        }
    }

    private long resolveResetUnit(String resetUnit) {
        Matcher matcher = NUMBER_PATTERN.matcher(resetUnit);
        int roundNum = 1;
        String unit;
        if (matcher.find()) {
            roundNum = Integer.parseInt(matcher.group());
            unit = resetUnit.substring(roundNum + "".length());
        } else {
            unit = resetUnit;
        }
        switch (unit) {
            case "y":
            case "Y":
                return ChronoUnit.YEARS.getDuration().toMillis() * roundNum;
            case "M":
                return ChronoUnit.MONTHS.getDuration().toMillis() * roundNum;
            case "d":
            case "D":
                return ChronoUnit.DAYS.getDuration().toMillis() * roundNum;
            case "h":
            case "H":
                return ChronoUnit.HOURS.getDuration().toMillis() * roundNum;
            case "m":
                return ChronoUnit.MINUTES.getDuration().toMillis() * roundNum;
            case "s":
            case "S":
                return ChronoUnit.SECONDS.getDuration().toMillis() * roundNum;
            default:
                throw new IllegalArgumentException(resetUnit + "无法解析");

        }
    }

    @Override
    public Object getValue() throws EvaluationException {
        if (!enableReset) {
            return count.incrementAndGet();
        }
        long now = Instant.now().toEpochMilli();
        long round = timeStamp.get();
        if (now - round >= durationNum) {
            synchronized (this) {
                if (now - round >= durationNum) {
                    count.set(0);
                    timeStamp.set(round + durationNum);
                }

            }
        }
        int index = count.incrementAndGet();
        if (index >= maxCount) {
            throw new IllegalStateException("填充大小为[" + numberFill + "],但是序号已经拍到了" + index + "建议调大数字");
        }
        StringBuilder sb = new StringBuilder(index + "");
        int length = sb.length();
        for (int i = 0; i < numberFill - length; i++) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

}
