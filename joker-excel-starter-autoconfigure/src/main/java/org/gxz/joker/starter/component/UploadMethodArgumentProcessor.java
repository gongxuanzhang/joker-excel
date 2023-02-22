package org.gxz.joker.starter.component;

import lombok.extern.slf4j.Slf4j;
import org.gxz.joker.starter.annotation.Upload;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.ServletRequest;
import java.util.Map;


/**
 * @author gxz gongxuanzhang@foxmail.com
 * 为了防止springmvc的参数解析把upload的参数混乱 自定义参数解析器直接把参数置为空集合
 **/
@Slf4j
public class UploadMethodArgumentProcessor implements HandlerMethodArgumentResolver {


    private final UploadAnalysis uploadAnalysis;

    public UploadMethodArgumentProcessor(UploadAnalysis uploadAnalysis) {
        this.uploadAnalysis = uploadAnalysis;
    }


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Upload.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        MultipartRequest request = webRequest.getNativeRequest(MultipartRequest.class);
        if (request == null) {
            log.warn("非上传请求 Upload注解无法解析 将赋值{}为null", parameter.getParameterName());
            return null;
        }
        MultipartFile file = request.getFile("file");
        ReadHolder<?> readHolder = uploadAnalysis.analysisData(file, parameter);
        WebDataBinder binder = binderFactory.createBinder(webRequest, readHolder.getErrorRows(),
                ComponentConstant.ERROR_ROW_BINDER_KEY);
        if (binder.getTarget() != null) {
            bindRequestParameters(binder, webRequest);
        }
        BindingResult bindingResult = binder.getBindingResult();
        Map<String, Object> model = bindingResult.getModel();
        model.put(ComponentConstant.ERROR_HEAD_BINDER_KEY, readHolder.getHead());
        mavContainer.removeAttributes(model);
        mavContainer.addAllAttributes(model);
        return readHolder.getData();
    }


    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
        Assert.state(servletRequest != null, "No ServletRequest");
        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
        servletBinder.bind(servletRequest);
    }

}
