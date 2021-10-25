package com.gxz.jokerexceltest;

import org.gxz.joker.starter.annotation.EnableJokerExcel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;

@SpringBootApplication
@EnableJokerExcel
public class JokerExcelTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(JokerExcelTestApplication.class, args);
    }

}
