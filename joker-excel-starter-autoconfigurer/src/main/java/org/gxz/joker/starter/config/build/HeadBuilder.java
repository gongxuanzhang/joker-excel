package org.gxz.joker.starter.config.build;

import lombok.Getter;
import org.springframework.util.Assert;

/**
 * @author gongxuanzhang
 */
@Getter
public class HeadBuilder extends JoinAbleBuilder {

    private String suffix;
    private String prefix;
    private ConcatSupplier suffixSupplier;
    private ConcatSupplier prefixSupplier;


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


}
