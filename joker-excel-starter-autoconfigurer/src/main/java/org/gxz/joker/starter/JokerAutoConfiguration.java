package org.gxz.joker.starter;


import org.gxz.joker.starter.component.ErrorRowsArgumentProcessor;
import org.gxz.joker.starter.component.ExportAspect;
import org.gxz.joker.starter.component.JokerBeanPostProcessor;
import org.gxz.joker.starter.component.UploadMethodArgumentProcessor;
import org.gxz.joker.starter.component.UploadAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/

@Import({JokerBeanPostProcessor.class,
        ExportAspect.class,
        UploadAnalysis.class,
        UploadMethodArgumentProcessor.class

})
@Configuration
public class JokerAutoConfiguration implements WebMvcConfigurer {


    @Autowired
    private UploadMethodArgumentProcessor uploadArgumentProcessor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(uploadArgumentProcessor);
        resolvers.add(new ErrorRowsArgumentProcessor());
    }

}
