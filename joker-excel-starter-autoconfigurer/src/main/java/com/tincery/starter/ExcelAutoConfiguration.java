package com.tincery.starter;


import com.tincery.starter.component.ExportAspect;
import com.tincery.starter.component.UploadArgumentProcessor;
import com.tincery.starter.component.UploadAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/

@Configuration
public class ExcelAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public ExportAspect exportAspect(){
        return new ExportAspect();
    }

    @Bean
    public UploadAspect updateAspect(){
        return new UploadAspect();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UploadArgumentProcessor());
    }

}
