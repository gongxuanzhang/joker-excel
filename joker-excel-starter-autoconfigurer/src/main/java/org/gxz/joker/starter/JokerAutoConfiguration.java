package org.gxz.joker.starter;


import org.gxz.joker.starter.component.ErrorRowsArgumentProcessor;
import org.gxz.joker.starter.component.ExportAspect;
import org.gxz.joker.starter.component.JokerBeanPostProcessor;
import org.gxz.joker.starter.component.JokerSpelFunction;
import org.gxz.joker.starter.component.UploadMethodArgumentProcessor;
import org.gxz.joker.starter.component.UploadAnalysis;
import org.gxz.joker.starter.config.build.JokerConfigurationDelegate;
import org.gxz.joker.starter.element.check.CheckComposite;
import org.gxz.joker.starter.element.check.RequireCheck;
import org.gxz.joker.starter.element.gardener.GardenerComposite;
import org.gxz.joker.starter.element.gardener.SelectGardener;
import org.gxz.joker.starter.expression.JokerExpressionParserComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/

@Import({JokerBeanPostProcessor.class,
        ExportAspect.class,
        UploadAnalysis.class,
        UploadMethodArgumentProcessor.class,
        JokerSpelFunction.class

})
@Configuration
public class JokerAutoConfiguration implements WebMvcConfigurer, EnvironmentAware {



    @Bean
    public GardenerComposite gardenerComposite(){
        GardenerComposite gardenerComposite = new GardenerComposite();
        gardenerComposite.addResolver(new SelectGardener());
        JokerConfigurationDelegate.registerGardener(gardenerComposite);
        return gardenerComposite;
    }

    @Bean
    public CheckComposite checkComposite(){
        CheckComposite checkComposite = new CheckComposite();
        checkComposite.addCheck(new RequireCheck());
        JokerConfigurationDelegate.registerCheck(checkComposite);
        return checkComposite;
    }

    @Autowired
    private UploadMethodArgumentProcessor uploadArgumentProcessor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(uploadArgumentProcessor);
        resolvers.add(new ErrorRowsArgumentProcessor());
    }

    @Override
    public void setEnvironment(Environment environment) {

    }
}
