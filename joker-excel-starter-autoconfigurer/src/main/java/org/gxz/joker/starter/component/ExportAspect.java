package org.gxz.joker.starter.component;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.iterators.ArrayListIterator;
import org.aspectj.lang.Signature;
import org.gxz.joker.starter.annotation.Export;
import org.gxz.joker.starter.element.ExcelDescription;
import org.gxz.joker.starter.element.gardener.GardenerComposite;
import org.gxz.joker.starter.exception.ExportReturnException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Aspect
public class ExportAspect implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    @Around(value = "@annotation(org.gxz.joker.starter.annotation.Export)")
    public Object exportAspect(ProceedingJoinPoint pjp) throws Throwable {
        // 校验环境内容
        MethodSignature sig = (MethodSignature) pjp.getSignature();
        Class<?> returnType = sig.getReturnType();
        if (!returnType.isAssignableFrom(Iterable.class)) {
            throw new ExportReturnException("导出的方法返回值必须是能迭代的！");
        }
        HttpServletResponse response =
                ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
        if (Objects.isNull(response)) {
            throw new IllegalStateException("拿不到response");
        }
        Iterable<?> result = (Iterable<?>) pjp.proceed();
        if (IterableUtils.isEmpty(result)) {
            throw new NullPointerException("无法解析出数据");
        }
        Class<?> beanType = result.iterator().next().getClass();
        ExcelDescription excelDescription = analysisExcelDesc(pjp, beanType);
        Workbook workbook = ExcelExportExecutor.writeWorkBook(result, excelDescription);
        workbook.setSheetName(0, excelDescription.getSheetName());
        ExportUtils.downLoadExcel(excelDescription.getExcelName(), response, workbook);
        return result;
    }

    private ExcelDescription analysisExcelDesc(ProceedingJoinPoint pjp, Class<?> beanType) {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Export export = method.getAnnotation(Export.class);
        ExcelDescription excelDescription = new ExcelDescription();
        excelDescription.setBeanType(beanType);
        excelDescription.fuseExcelName(new ExportNameWrapper(method, beanType, pjp.getArgs(), applicationContext));
        excelDescription.fuseSheetName(new ExportSheetWrapper(export));
        excelDescription.fuseField(new ExportFieldWrapper(export), new BeanClassWrapper(beanType));
        return excelDescription;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
