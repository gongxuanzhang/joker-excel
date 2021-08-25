package com.tincery.starter.convert;


import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ConverterRegistry {
    /**
     * 默认类型转换器
     */
    // 以下是通过被转换的类型 拿到对应的转换器

    private Map<Type, Converter<?>> defaultConverterMap;

    private volatile Map<Type, Converter<?>> customConverterMap;

    public static final Converter<?> DEFAULT_CONVERTER = new ManagerConverter();

    public static ConverterRegistry getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public Converter<?> getConverterByType(Type type) {
        Converter<?> defaultConvert = defaultConverterMap.getOrDefault(type, null);
        if (defaultConvert != null) {
            return defaultConvert;
        }

        if (customConverterMap == null) {
            return null;
        }
        return customConverterMap.getOrDefault(type, null);
    }


    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static final ConverterRegistry INSTANCE = new ConverterRegistry();

    }

    private ConverterRegistry() {
        defaultConverter();
    }

    private ConverterRegistry defaultConverter() {
        defaultConverterMap = new LinkedHashMap<>();
        defaultConverterMap.put(ManagerConverter.class, DEFAULT_CONVERTER);
        return this;
    }

    public ConverterRegistry putCustom(Type type, Converter<?> converter) {
        if (customConverterMap == null) {
            synchronized (this) {
                if (customConverterMap == null) {
                    customConverterMap = new ConcurrentHashMap<>();
                }
            }
        }
        this.customConverterMap.put(type, converter);
        return this;
    }


}
