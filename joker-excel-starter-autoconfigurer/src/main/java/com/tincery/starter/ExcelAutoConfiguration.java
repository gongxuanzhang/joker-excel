package com.tincery.starter;


import com.tincery.starter.component.ExportAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/

@Configuration
@Slf4j
public class ExcelAutoConfiguration {

    @Bean
    public ExportAspect exportAspect(){
        return new ExportAspect();
    }

}
