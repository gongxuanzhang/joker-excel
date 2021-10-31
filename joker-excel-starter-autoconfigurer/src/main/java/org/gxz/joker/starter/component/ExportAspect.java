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
import org.gxz.joker.starter.expression.JokerExpressionParser;
import org.gxz.joker.starter.tool.ExcelExportExecutor;
import org.gxz.joker.starter.tool.ExportUtils;
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
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Aspect
public class ExportAspect implements ApplicationContextAware, EnvironmentAware {

    private ApplicationContext applicationContext;

    private ConfigurablePropertyResolver environmentResolver;

    private Map<Method, ExpressionParser> methodResolvers = new ConcurrentHashMap<>();


    @Around(value = "@annotation(org.gxz.joker.starter.annotation.Export)")
    public Object exportAspect(ProceedingJoinPoint pjp) throws Throwable {
        // 校验环境内容
        try{
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
            Iterable<?> result = (Iterable<?>) pjp.proceed();
            if (IterableUtils.isEmpty(result)) {
                throw new NullPointerException("无法解析出数据");
            }
            Class<?> beanType = result.iterator().next().getClass();
            ExcelDescription excelDescription = analysisExcelDesc(pjp, beanType);
            Workbook workbook = ExcelExportExecutor.writeWorkBook(result, excelDescription);
            workbook.setSheetName(0, excelDescription.getSheetName());
            ExportUtils.downLoadExcel(excelDescription.getExcelName(), response, workbook);
        }finally {
            ThreadMethodHolder.clear();
        }
        return null;
    }

    private ExcelDescription analysisExcelDesc(ProceedingJoinPoint pjp, Class<?> beanType) {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Export export = method.getAnnotation(Export.class);
        ExpressionParser methodParser = methodResolvers.computeIfAbsent(method, k -> new JokerExpressionParser());
        ConcatPropertyResolver resolver = new ConcatPropertyResolver(this.environmentResolver, methodParser);
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
    }

    public static void main(String[] args) {
        ExpressionParser parser = new JokerExpressionParser();
        ParserContext parserContext = new ParserContext() {
            @Override
            public boolean isTemplate() {
                return true;
            }

            @Override
            public String getExpressionPrefix() {
                return "${";
            }

            @Override
            public String getExpressionSuffix() {
                return "}";
            }
        };
        String template = "${asdf}";
        Expression expression = parser.parseExpression(template, parserContext);
        System.out.println(expression.getValue());


        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("name", "路人甲java");
        context.setVariable("lesson", "Spring系列");

        //获取name变量，lesson变量
        String name = parser.parseExpression("#name").getValue(context, String.class);
        System.out.println(name);
        String lesson = parser.parseExpression("#lesson").getValue(context, String.class);
        System.out.println(lesson);

        //StandardEvaluationContext构造器传入root对象，可以通过#root来访问root对象
        context = new StandardEvaluationContext(new String[]{"a", "b"});
        Object value = parser.parseExpression("#root").getValue(context);
        System.out.println(value);
        String rootObj = parser.parseExpression("#root").getValue(context, String.class);
        System.out.println(rootObj);

        //#this用来访问当前上线文中的对象
        String thisObj = parser.parseExpression("#this").getValue(context, String.class);
        System.out.println(thisObj);

    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environmentResolver = (ConfigurablePropertyResolver) environment;
        environmentResolver.resolvePlaceholders("${asdf}");
    }
}
