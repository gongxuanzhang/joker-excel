package org.gxz.joker.starter.component;

import org.apache.commons.collections4.IterableUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.gxz.joker.starter.annotation.Export;
import org.gxz.joker.starter.element.ExcelDescription;
import org.gxz.joker.starter.exception.ExportReturnException;
import org.gxz.joker.starter.expression.ConcatPropertyResolver;
import org.gxz.joker.starter.expression.JokerExpressionParserAdapter;
import org.gxz.joker.starter.expression.JokerExpressionParserComposite;
import org.gxz.joker.starter.service.ExcelCreator;
import org.gxz.joker.starter.service.SimpleExcelCreator;
import org.gxz.joker.starter.tool.ExportUtils;
import org.gxz.joker.starter.tool.ReflectUtil;
import org.gxz.joker.starter.tool.ThreadMethodHolder;
import org.gxz.joker.starter.wrapper.BeanClassWrapper;
import org.gxz.joker.starter.wrapper.ExportFieldWrapper;
import org.gxz.joker.starter.wrapper.ExportNameWrapper;
import org.gxz.joker.starter.wrapper.ExportSheetWrapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.env.Environment;
import org.springframework.expression.ExpressionParser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Aspect
public class ExportAspect implements ApplicationContextAware, EnvironmentAware {

    private ApplicationContext applicationContext;

    private ConfigurablePropertyResolver environmentResolver;

    private JokerExpressionParserComposite parserComposite;


    @Around(value = "@annotation(org.gxz.joker.starter.annotation.Export)")
    public Object exportAspect(ProceedingJoinPoint pjp) throws Throwable {
        // 校验环境内容
        try {
            MethodSignature sig = (MethodSignature) pjp.getSignature();
            ThreadMethodHolder.setMethod(sig.getMethod());
            Class<?> returnType = sig.getReturnType();
            if (!(Iterable.class.isAssignableFrom(returnType))) {
                throw new ExportReturnException("导出的方法返回值必须是能迭代的！");
            }
            HttpServletResponse response =
                    ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
            if (Objects.isNull(response)) {
                throw new IllegalStateException("拿不到response");
            }
            Class<?> beanType;
            Iterable<?> result = (Iterable<?>) pjp.proceed();
            if (IterableUtils.isEmpty(result)) {
                beanType = ReflectUtil.getMethodResultGenericity(sig.getMethod());
            } else {
                beanType = result.iterator().next().getClass();
            }
            ExcelDescription excelDescription = analysisExcelDesc(pjp, beanType);
            ExcelCreator excelCreator = new SimpleExcelCreator(result, excelDescription);
            Workbook workbook = excelCreator.create();
            workbook.setSheetName(0, excelDescription.getSheetName());
            ExportUtils.downLoadExcel(excelDescription.getExcelName(), response, workbook);
        } finally {
            ThreadMethodHolder.clear();
        }
        return null;
    }

    private ExcelDescription analysisExcelDesc(ProceedingJoinPoint pjp, Class<?> beanType) {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Export export = method.getAnnotation(Export.class);
        ExpressionParser adapter = new JokerExpressionParserAdapter(parserComposite);
        ConcatPropertyResolver resolver = new ConcatPropertyResolver(this.environmentResolver, adapter);
        ExcelDescription excelDescription = new ExcelDescription(resolver);
        excelDescription.setBeanType(beanType);
        excelDescription.fuseExcelName(new ExportNameWrapper(method, beanType, pjp.getArgs(), applicationContext));
        excelDescription.fuseSheetName(new ExportSheetWrapper(export));
        excelDescription.fuseField(new ExportFieldWrapper(export), new BeanClassWrapper(beanType));
        return excelDescription;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.parserComposite = new JokerExpressionParserComposite(applicationContext);

    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environmentResolver = (ConfigurablePropertyResolver) environment;
    }
}
