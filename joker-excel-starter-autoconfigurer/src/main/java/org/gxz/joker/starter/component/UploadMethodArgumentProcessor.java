package org.gxz.joker.starter.component;

import org.gxz.joker.starter.annotation.Upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;


/**
 * @author gxz gongxuanzhang@foxmail.com
 * 为了防止springmvc的参数解析把upload的参数混乱 自定义参数解析器直接把参数置为空集合
 **/
@Slf4j
public class UploadMethodArgumentProcessor implements HandlerMethodArgumentResolver {


    @Autowired
    UploadAnalysis uploadAnalysis;


    @Override

    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Upload.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        MultipartRequest nativeRequest = webRequest.getNativeRequest(MultipartRequest.class);
        if (nativeRequest == null) {
            log.warn("非上传请求 Upload注解无法解析 将赋值{}为null", parameter.getParameterName());
            return null;
        }
        MultipartFile file = nativeRequest.getFile("file");
        return uploadAnalysis.analysisData(file, parameter);
    }


}
