package com.tincery.starter.component;

import com.tincery.starter.annotation.Upload;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collections;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 为了防止springmvc的参数解析把upload的参数混乱 自定义参数解析器直接把参数置为空集合
 **/
public class UploadArgumentProcessor implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Upload.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return Collections.emptyList();
    }
}
