package org.gxz.joker.starter.wrapper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.gxz.joker.starter.annotation.Export;
import org.gxz.joker.starter.element.ExcelNameOverlayable;
import org.gxz.joker.starter.element.OrderConstant;
import org.gxz.joker.starter.service.ExcelNameFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ExportNameWrapper implements ExcelNameOverlayable {


    public static Cache<Class<? extends ExcelNameFactory>, ExcelNameFactory> ttlCache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.HOURS)
            .build();

    public static Map<Class<? extends ExcelNameFactory>, ExcelNameFactory> singletonCache = new ConcurrentHashMap<>();

    private final Export export;
    private final Method method;
    private final Class<?> beanClass;
    private final Object[] args;
    private final ApplicationContext applicationContext;

    public ExportNameWrapper(Method method, Class<?> beanClass, Object[] args, ApplicationContext applicationContext) {
        this.export = method.getAnnotation(Export.class);
        this.method = method;
        this.beanClass = beanClass;
        this.args = args;
        this.applicationContext = applicationContext;
    }


    @Override
    public int getOrder() {
        return OrderConstant.EXPORT_ANNOTATION_ORDER;
    }


    @Override
    public String getValue() {
        if (export == null) {
            return defaultValue();
        }
        Class<? extends ExcelNameFactory> factoryClass = export.nameFactory();
        if (factoryDefault(export.nameFactory())) {
            return export.value();
        }
        if (applicationContext != null) {
            String[] iocForFactory = applicationContext.getBeanNamesForType(factoryClass);
            if (iocForFactory.length > 1) {
                throw new IllegalStateException("ioc中包含多个" + factoryClass.getName() + "实例， 无法指定执行目标工厂");
            }
            if (iocForFactory.length == 1) {
                String name = iocForFactory[0];
                return applicationContext.getBean(name, factoryClass).getExcelName(beanClass, args, method);
            }
        }

        try {
            ExcelNameFactory instance = ttlCache.getIfPresent(factoryClass);
            if (instance == null) {
                instance = singletonCache.get(factoryClass);
                if (instance == null) {
                    instance = factoryClass.newInstance();
                    if (instance.singleton()) {
                        singletonCache.put(factoryClass, instance);
                    } else {
                        ttlCache.put(factoryClass, instance);
                    }
                }
            }
            return instance.getExcelName(beanClass, args, method);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return export.value();
        }

    }
}
