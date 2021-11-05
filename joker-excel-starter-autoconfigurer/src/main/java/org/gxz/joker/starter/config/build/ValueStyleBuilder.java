package org.gxz.joker.starter.config.build;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 作为属性聚合进ClassStyleBuilder
 * 独立创建没有意义，层级关系和FiledStyleBuilder相同
 * <p>
 * 此类定义了一个Value规则和一个样式定义。
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Slf4j
public class ValueStyleBuilder implements StyleBuilder {


    final StyleBuilder parent;

    Predicate<String> valuePredicate;

    Consumer<CellStyle> cellStyleConsumer;


    public ValueStyleBuilder(StyleBuilder parent, Predicate<String> valuePredicate) {
        this.parent = parent;
        this.valuePredicate = valuePredicate;
    }

    @Override
    public ClassStyleBuilder whenClass(Class<?> typeBean) {
        if (!chainPermit()) {
            log.warn("{}未定义规则，将不会生效", valuePredicate);
        }
        return parent.whenClass(typeBean);
    }


    @Override
    public boolean chainPermit() {
        return this.cellStyleConsumer != null;
    }


    /**
     * 设置Value级别的样式
     *
     * @param cellStyleConsumer 样式内容
     * @return builder
     **/
    @Override
    public ValueStyleBuilder style(Consumer<CellStyle> cellStyleConsumer) {
        this.cellStyleConsumer = cellStyleConsumer;
        return this;
    }

}
