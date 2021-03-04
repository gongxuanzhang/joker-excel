package com.tincery.starter.component;

import com.tincery.starter.annotation.ExcelData;
import com.tincery.starter.annotation.Upload;
import com.tincery.starter.tool.ExcelExportExecutor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Aspect
public class UploadAspect {


    /***
     * 上传文件解析
     **/
    @Around("withUpload() && withMultipart()")
    public Object parseFile(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature sig = (MethodSignature) pjp.getSignature();
        Object[] args = pjp.getArgs();
        int multiPartIndex = findMultiPartIndex(args);
        int uploadIndex = findUploadIndex(sig.getMethod());
        Class<?> dataClass = getGenericClass(sig.getMethod(), uploadIndex);
        Assert.state(dataClass.isAnnotationPresent(ExcelData.class), "类" + dataClass.getName() + "没有加ExcelData注解  " +
                "无法解析反序列化内容");
        MultipartHttpServletRequest request = (MultipartHttpServletRequest) args[multiPartIndex];
        List<?> data = analysisData(request, dataClass);
        args[uploadIndex] = data;
        return pjp.proceed(args);
    }

    private List<?> analysisData(MultipartHttpServletRequest request, Class<?> dataClass) {
        MultipartFile file = request.getFile("file");
        Assert.notNull(file, "找不到上传的文件");
        String fileName = file.getOriginalFilename();
        Assert.state(fileName.endsWith(".xlsx"), "文件格式错误 请下载数据修改后上传");
        try (InputStream fileInputStream = file.getInputStream()) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
            return ExcelExportExecutor.readWorkBook(xssfWorkbook, dataClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取方法中的List泛型内容
     *
     * @param method      反射方法类
     * @param uploadIndex 方法参数中第几个是加了注解的
     * @return 返回泛型的Class内容
     **/
    private Class<?> getGenericClass(Method method, int uploadIndex) {
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        ParameterizedType genericParameterType1 = (ParameterizedType) genericParameterTypes[uploadIndex];
        return (Class<?>) genericParameterType1.getActualTypeArguments()[0];
    }


    private int findMultiPartIndex(Object[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof MultipartHttpServletRequest) {
                return i;
            }
        }
        throw new IllegalArgumentException("参数应该有一个MultipartHttpServletRequest类");
    }

    private int findUploadIndex(Method method) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(Upload.class)) {
                return i;
            }
        }
        throw new IllegalArgumentException("找不到Upload注解");
    }


    /***
     * 参数拥有{@link com.tincery.starter.annotation.Upload}注解的方法
     **/
    @Pointcut("execution(* *(..,@com.tincery.starter.annotation.Upload (*),..))")
    public void withUpload() {

    }

    /***
     * 参数用有MultipartHttpServletRequest 类型的
     * 表示一个上传方法
     **/
    @Pointcut("execution(* *(..,org.springframework.web.multipart.MultipartHttpServletRequest,..))")
    public void withMultipart() {

    }



}


