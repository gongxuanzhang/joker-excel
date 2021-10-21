package org.gxz.joker.starter;


import org.gxz.joker.starter.component.ExportAspect;
import org.gxz.joker.starter.component.UploadMethodArgumentProcessor;
import org.gxz.joker.starter.component.UploadAnalysis;
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
    public ExportAspect exportAspect() {
        return new ExportAspect();
    }

    @Bean
    public UploadAnalysis updateAspect() {
        return new UploadAnalysis();
    }

    @Bean
    public UploadMethodArgumentProcessor uploadArgumentProcessor() {
        return new UploadMethodArgumentProcessor();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(uploadArgumentProcessor());
    }

}
