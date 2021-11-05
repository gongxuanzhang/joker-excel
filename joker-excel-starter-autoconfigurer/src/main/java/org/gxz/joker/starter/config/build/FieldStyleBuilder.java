package org.gxz.joker.starter.config.build;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 记录字段样式构建，独立存在没有意义,有上下文关系
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Slf4j
public class FieldStyleBuilder implements ValueChainStyleBuilder {


    final ClassStyleBuilder parent;

    List<ValueStyleBuilder> child;

    final String filedName;

    Consumer<CellStyle> cellStyleConsumer;

    public FieldStyleBuilder(ClassStyleBuilder parent, String filedName) {
        this.parent = parent;
        this.filedName = filedName;
    }


    @Override
    public ClassStyleBuilder whenClass(Class<?> beanType) {
        if (!chainPermit()) {
            log.warn("{}的{}属性被设置规则，但是没设置样式，将不会生效", parent.getBeanType(), this.filedName);
        }
        return parent.whenClass(beanType);
    }

    @Override
    public boolean chainPermit() {
        return !(CollectionUtils.isEmpty(child) && this.cellStyleConsumer == null);
    }

    /**
     * 设置字段级别的style
     *
     * @param cellStyleConsumer style
     * @return builder
     **/
    @Override
    public FieldStyleBuilder style(Consumer<CellStyle> cellStyleConsumer) {
        this.cellStyleConsumer = cellStyleConsumer;
        return this;
    }


    /**
     *
     * 定义字段级别的 值规则
     * @param valuePredicate 字段级别的规则
     * @return builder
     **/
    @Override
    public ValueStyleBuilder whenValue(Predicate<String> valuePredicate) {
        if(child==null){
            this.child = new LinkedList<>();
        }
        ValueStyleBuilder valueStyleBuilder = new ValueStyleBuilder(this, valuePredicate);
        this.child.add(valueStyleBuilder);
        return valueStyleBuilder;
    }
}
