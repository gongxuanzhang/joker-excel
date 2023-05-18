package org.gxz.joker.starter.convert;


import org.gxz.joker.starter.exception.JokerRuntimeException;

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


    /**
     * 通过被转换的类型获取转换器实例
     *
     * @param type 被转换的类型
     * @return 转换器实例
     **/
    public Converter<?> getConverterByType(Type type) {
        Converter<?> defaultConvert = defaultConverterMap.getOrDefault(type, null);
        if (defaultConvert != null) {
            return defaultConvert;
        }
        if (customConverterMap == null) {
            return null;
        }
        Converter<?> finalConverter = customConverterMap.getOrDefault(type, null);
        if (finalConverter == null) {
            return DEFAULT_CONVERTER;
        }
        return finalConverter;
    }

    /**
     * 通过转换器的类型获取转换器实例
     * 如果没有，就创建转换器，同时加入注册
     *
     * @param converterClass 转换器类型
     * @return 转换实例
     **/
    public Converter<?> getConverterAndEnrol(Class<? extends Converter<?>> converterClass) {

        Converter<?> converterByType = getConverterByType(converterClass);
        if (converterByType == null) {
            try {
                putCustom(converterClass, converterClass.newInstance());
                converterByType = getConverterByType(converterClass);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new JokerRuntimeException("无法解析转换器");
            }
        }
        return converterByType;
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
                    customConverterMap = new ConcurrentHashMap<>(16);
                }
            }
        }
        this.customConverterMap.put(type, converter);
        return this;
    }


}
