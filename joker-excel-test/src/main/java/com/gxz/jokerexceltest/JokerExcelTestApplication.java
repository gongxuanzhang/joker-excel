package com.gxz.jokerexceltest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;

@SpringBootApplication
public class JokerExcelTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(JokerExcelTestApplication.class, args);
    }

}
