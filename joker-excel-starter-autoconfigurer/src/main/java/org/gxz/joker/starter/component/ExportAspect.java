package org.gxz.joker.starter.component;

import org.gxz.joker.starter.annotation.Export;
import org.gxz.joker.starter.element.ExcelDescription;
import org.gxz.joker.starter.service.ExcelNameFactory;
import org.gxz.joker.starter.service.DefaultSupport;
import org.gxz.joker.starter.tool.ExcelExportExecutor;
import org.gxz.joker.starter.tool.ExportUtils;
import org.gxz.joker.starter.wrapper.BeanClassWrapper;
import org.gxz.joker.starter.wrapper.ExportFieldWrapper;
import org.gxz.joker.starter.wrapper.ExportNameWrapper;
import org.apache.poi.ss.usermodel.Workbook;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.gxz.joker.starter.wrapper.ExportSheetWrapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Aspect
public class ExportAspect implements ApplicationContextAware {

    private volatile Map<Class<? extends ExcelNameFactory>, ExcelNameFactory> factoryCache;

    private ApplicationContext applicationContext;



    @Around(value = "@annotation(org.gxz.joker.starter.annotation.Export)")
    public Object logJournal(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature sig = (MethodSignature) pjp.getSignature();
        Class<?> returnType = sig.getReturnType();
        if (!returnType.isAssignableFrom(List.class)) {
            throw new IllegalAccessException("返回值必须是list");
        }
        List<?> result = (List<?>) pjp.proceed();
       if(CollectionUtils.isEmpty(result)){
           throw new NullPointerException("无法解析出数据");
       }
        Method method = sig.getMethod();
        Export export = method.getAnnotation(Export.class);
        ExcelDescription excelDescription = new ExcelDescription();
        Class<?> beanClass = result.get(0).getClass();
        excelDescription.fuseExcelName(new ExportNameWrapper(method,beanClass,pjp.getArgs(),applicationContext));
        excelDescription.fuseSheetName(new ExportSheetWrapper(export));
        excelDescription.fuseField(new ExportFieldWrapper(export),new BeanClassWrapper(beanClass));
        HttpServletResponse response =
                ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
        if (Objects.isNull(response)) {
            throw new IllegalStateException("can't get response");
        }
        Workbook workbook = ExcelExportExecutor.writeWorkBook(result, excelDescription);
        workbook.setSheetName(0, excelDescription.getSheetName());
        ExportUtils.downLoadExcel(excelDescription.getExcelName(), response, workbook);
        return null;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
