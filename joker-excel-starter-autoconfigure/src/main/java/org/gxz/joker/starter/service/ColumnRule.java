package org.gxz.joker.starter.service;

import lombok.Data;

import java.util.Optional;

/**
 * 列规则
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
public class ColumnRule implements Comparable<ColumnRule> {


    /**
     * 列序
     **/
    private int order;

    /**
     * 解析的数据类型
     **/
    private Class<?> beanType;
    /**
     * 表头规则
     **/
    private HeadRule headRule;

    /**
     * 数据规则
     **/
    private DataRule dataRule;


    public String getHeadValue() {
        return Optional.ofNullable(this.headRule).map(HeadRule::getName).orElse(null);
    }


    @Override
    public int compareTo(ColumnRule other) {
        return Integer.compare(this.order, other.order);
    }
}
