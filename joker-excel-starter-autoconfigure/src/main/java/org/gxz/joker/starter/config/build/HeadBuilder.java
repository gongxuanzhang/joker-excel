package org.gxz.joker.starter.config.build;

import lombok.Getter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author gongxuanzhang
 */
@Getter
public class HeadBuilder extends JoinAbleBuilder implements ValueChainStyleBuilder {

    private String suffix;
    private String prefix;
    private ConcatSupplier suffixSupplier;
    private ConcatSupplier prefixSupplier;

    // 设置样式相应内容

    /**
     * 全局表头配置内容
     **/
    private Consumer<CellStyle> globalHeadCellStyle;
    /**
     * 关于Class的配置
     **/
    private List<ClassStyleBuilder> classStyleBuilderList;
    /**
     * 关于值的配置
     **/
    private List<ValueStyleBuilder> valueStyleBuilderList;


    public HeadBuilder(JokerBuilder jokerBuilder) {
        super(jokerBuilder);
    }


    /**
     * 设置前缀
     **/
    public HeadBuilder prefix(String prefix) {
        Assert.isNull(this.prefixSupplier, "不能同时设置 prefixSupplier 和 prefix");
        this.prefix = prefix;
        return this;
    }


    /**
     * 设置前缀
     **/
    public HeadBuilder prefix(ConcatSupplier prefixSupplier) {
        Assert.isNull(this.prefix, "不能同时设置 prefixSupplier 和 prefix");
        this.prefixSupplier = prefixSupplier;
        return this;
    }


    /**
     * 设置后缀
     **/
    public HeadBuilder suffix(String suffix) {
        Assert.isNull(this.suffixSupplier, "不能同时设置 suffixSupplier 和 suffix");
        this.suffix = suffix;
        return this;
    }

    /**
     * 设置后缀
     **/
    public HeadBuilder suffix(ConcatSupplier suffixSupplier) {
        Assert.isNull(this.suffix, "不能同时设置 suffixSupplier 和 suffix");
        this.suffixSupplier = suffixSupplier;
        return this;
    }

    /**
     *
     * 向容器中添加class builder 返回此builder
     * @param beanType 类型内容
     * @return 被添加到容器中的builder
     **/
    @Override
    public ClassStyleBuilder whenClass(Class<?> beanType) {
        if(classStyleBuilderList == null){
            classStyleBuilderList = new LinkedList<>();
        }
        ClassStyleBuilder classStyleBuilder = new ClassStyleBuilder(this, beanType);
        classStyleBuilderList.add(classStyleBuilder);
        return classStyleBuilder;
    }

    @Override
    public boolean chainPermit() {
        return true;
    }

    /**
     *
     * 设置全局级别的 值内容定义
     *
     * @param valuePredicate 定义内容本体
     * @return builder
     **/
    @Override
    public ValueStyleBuilder whenValue(Predicate<String> valuePredicate) {
        if(valueStyleBuilderList == null){
            this.valueStyleBuilderList = new LinkedList<>();
        }
        ValueStyleBuilder valueStyleBuilder = new ValueStyleBuilder(this, valuePredicate);
        this.valueStyleBuilderList.add(valueStyleBuilder);
        return valueStyleBuilder;
    }


    /**
     *
     * 直接设置全局 表头内容
     * @param styleConsumer 设置规则
     * @return builder
     **/
    @Override
    public HeadBuilder style(Consumer<CellStyle> styleConsumer) {
        this.globalHeadCellStyle = styleConsumer;
        return this;
    }



}
