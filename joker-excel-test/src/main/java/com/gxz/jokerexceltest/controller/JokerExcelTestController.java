package com.gxz.jokerexceltest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@RestController
public class JokerExcelTestController {


    @GetMapping("/")
    public ModelAndView toHtml(){
        return new ModelAndView("test");
    }

}
