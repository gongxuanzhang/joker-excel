package com.gxz.jokerexceltest.controller;

import com.tincery.starter.annotation.Export;
import com.tincery.starter.annotation.Upload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@RestController
public class JokerExcelTestController {


    @GetMapping("/test")
    public ModelAndView toHtml() {
        return new ModelAndView("test.html");
    }

    @Export("导出的名字")
    @GetMapping("/export")
    public List<User> export() {
        return data();
    }

    private List<User> data() {
        List<User> exportData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setName("张" + i);
            user.setAge(i);
            user.setBirthday(LocalDateTime.now().minusDays(i));
            user.setSex(i % 2 == 0 ? "男" : "女");
            exportData.add(user);
        }
        return exportData;
    }

    @PostMapping("/upload")
    public Map<String, Object> upload(@Upload List<User> users) {
        for (User user : users) {
            System.out.println(user);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", users);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

}
