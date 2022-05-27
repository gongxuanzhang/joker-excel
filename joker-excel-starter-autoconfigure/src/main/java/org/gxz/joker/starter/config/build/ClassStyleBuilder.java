package org.gxz.joker.starter.config.build;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 表头样式设置
 * 独立存在没有意义，需要和父类指向共同标识
 * 记录Class内容, 有一个父指向，指向一个HeadBuilder
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Slf4j
public class ClassStyleBuilder implements FieldChainStyleBuilder, ValueChainStyleBuilder {

    @Getter
    final Class<?> beanType;

    final HeadBuilder parent;

    List<FieldStyleBuilder> fieldChildren;

    List<ValueStyleBuilder> valueChildren;

    Consumer<CellStyle> styleConsumer;

    boolean permit = false;

    public ClassStyleBuilder(HeadBuilder headBuilder, Class<?> beanType) {
        this.beanType = beanType;
        this.parent = headBuilder;
    }


    /**
     * 辅助链式编程， 包装了父级的whenClass方法
     *
     * @param beanType 类
     * @return 返回一个新的 classStyleBuilder 同 HeadBuilder.whenClass()
     **/
    @Override
    public ClassStyleBuilder whenClass(Class<?> beanType) {
        if (!chainPermit()) {
            log.warn("{}没有定义样式，将不生效", this.beanType.getName());
        }
        return parent.whenClass(beanType);
    }

    @Override
    public boolean chainPermit() {
        return this.permit;
    }


    @Override
    public ValueStyleBuilder whenValue(Predicate<String> valuePredicate) {
        this.permit = true;
        if (this.valueChildren == null) {
            this.valueChildren = new LinkedList<>();
        }
        ValueStyleBuilder valueStyleBuilder = new ValueStyleBuilder(this, valuePredicate);
        this.valueChildren.add(valueStyleBuilder);
        return valueStyleBuilder;

    }

    /**
     * 创建一个新filedStyle
     *
     * @param fieldName 属性名
     * @return 返回个啥
     **/
    @Override
    public FieldStyleBuilder whenField(String fieldName) {
        this.permit = true;
        if (fieldChildren == null) {
            fieldChildren = new LinkedList<>();
        }
        FieldStyleBuilder fieldStyleBuilder = new FieldStyleBuilder(this, fieldName);
        fieldChildren.add(fieldStyleBuilder);
        return fieldStyleBuilder;
    }


    /**
     * 设置Class级别的样式
     *
     * @param styleConsumer 样式
     * @return builder
     **/
    @Override
    public ClassStyleBuilder style(Consumer<CellStyle> styleConsumer) {
        this.permit = true;
        this.styleConsumer = styleConsumer;
        return this;
    }


}
