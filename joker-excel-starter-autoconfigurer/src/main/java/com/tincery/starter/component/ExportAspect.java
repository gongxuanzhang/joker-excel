package com.tincery.starter.component;

import com.tincery.starter.annotation.Export;
import com.tincery.starter.service.ExcelNameFactory;
import com.tincery.starter.service.NoneNameFactory;
import com.tincery.starter.tool.ExcelExportExecutor;
import com.tincery.starter.tool.ExportUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Aspect
public class ExportAspect {

    private volatile Map<Class<? extends ExcelNameFactory>, ExcelNameFactory> factoryCache;

    private static final String EXCEL_SUFFIX = ".xlsx";


    @Around(value = "@annotation(com.tincery.starter.annotation.Export)")
    public Object logJournal(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature sig = (MethodSignature) pjp.getSignature();
        Class<?> returnType = sig.getReturnType();
        if (!returnType.isAssignableFrom(List.class)) {
            throw new IllegalAccessException("返回值必须是list");
        }
        List<?> result = (List<?>) pjp.proceed();
        Method method = sig.getMethod();
        Export export = method.getAnnotation(Export.class);
        HttpServletResponse httpServletResponse = getArgsResponse(pjp);
        Workbook workbook = ExcelExportExecutor.writeWorkBook(result);
        Class<?> beanClass = result.get(0).getClass();
        String excelName = getExcelName(export, beanClass, pjp.getArgs(), method);
        workbook.setSheetName(0, export.sheetName());
        ExportUtils.downLoadExcel(excelName, httpServletResponse, workbook);
        return null;
    }





    /**
     * 通过注解导出文件名称
     *
     * @param export 方法上面的Export注解信息
     * @param args 切入点的参数
     * @param method 切入点方法
     * @return 返回导出的excel名称
     **/
    private String getExcelName(Export export, Class<?> beanClass, Object[] args, Method method) {
        String name;
        Class<? extends ExcelNameFactory> factoryClass = export.nameFactory();
        if (factoryClass == NoneNameFactory.class) {
            name = export.value();
        } else {
            ExcelNameFactory instance = getInstance(factoryClass);
            name = instance.getExcelName(beanClass, args, method);
        }

        if (!name.endsWith(EXCEL_SUFFIX)) {
            name += EXCEL_SUFFIX;
        }
        return name;

    }


    private ExcelNameFactory getInstance(Class<? extends ExcelNameFactory> factoryClass) {
        if (factoryCache == null) {
            synchronized (this) {
                if (factoryCache == null) {
                    factoryCache = new ConcurrentHashMap<>();
                }
            }
        }
        ExcelNameFactory instance = factoryCache.computeIfAbsent(factoryClass, (k) -> {
            try {
                return k.getConstructor().newInstance();
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                return null;
            }
        });
        String message = "无法创建[" + factoryClass.getName() + "]类的实例，请检查是否提供了无参构造方法";
        Assert.notNull(instance, message);
        return instance;
    }

    private HttpServletResponse getArgsResponse(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletResponse) {
                return (HttpServletResponse) arg;
            }
        }
        throw new IllegalArgumentException("方法参数必须提供HttpServletResponse");
    }


}
