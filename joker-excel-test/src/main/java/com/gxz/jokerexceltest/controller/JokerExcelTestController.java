package com.gxz.jokerexceltest.controller;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.gxz.joker.starter.annotation.ErrorRows;
import org.gxz.joker.starter.annotation.Export;
import org.gxz.joker.starter.annotation.Upload;
import org.gxz.joker.starter.service.ErrorRow;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@RestController
public class JokerExcelTestController {


    @GetMapping("/test")
    public ModelAndView toHtml() {
        return new ModelAndView("test.html");
    }


    @Export(value = "配置了sheetName的导出", sheetName = "这是sheet的名字")
    @GetMapping("/export/1")
    public List<User> exportSheetName() {
        return data();
    }

    @Export(value = "没有name", ignore = "age")
    @GetMapping("/export/2")
    public List<User> exportWithExportConfigurationAnnotation() {
        return data();
    }

    @Export(value = "只有name", include = "name")
    @GetMapping("/export/3")
    public List<User> export3() {
        return data();
    }

    @Export(value = "只有name", include = "name", nameFactory = TimeStampFactory.class)
    @GetMapping("/export/4")
    public List<User> export4() {
        return data();
    }

    @Export(nameFactory = TimeStampFactory.class)
    @GetMapping("/custom")
    public List<User> custom() {
        return new ArrayList<>();
    }


    @Export(configurationClass = IocJokerConfiguration.class)
    @GetMapping("/export/configuration/ioc")
    public List<User> exportWithExportAndSpringIoc() {
        return data();
    }


    @Export("张三")
    @GetMapping("/export/configuration/aaa")
    public List<User> aaa() {
        return data();
    }


    @Export(value = "${auto|4|m}正常导出${JAVA_HOME}")
    @GetMapping("/export")
    public List<User> export() {
        return data();
    }

    @Export(value = "${auto|5|m}正常导出${JAVA_HOME}")
    @GetMapping("/export1")
    public List<User> export1() {
        return data();
    }


    @PostMapping("/t")
    public String test(User user, BindingResult bindingResult) {
        return "asdf";
    }

    @PostMapping("/tt")
    public String testt(@Valid User user, BindingResult bindingResult) {
        return "asdf";
    }


    private List<User> data() {
        List<User> exportData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setName("张" + i);
            user.setAge(i);
            user.setBirthday(LocalDateTime.now().minusDays(i));
            user.setSex(i % 2 == 0 ? "男" : "女");
            user.setEmail(UUID.randomUUID().toString().substring(8) +"@gmail.com");
            exportData.add(user);
        }
        exportData.add(new User().setName("张1"));
        return exportData;
    }

    @PostMapping("/upload")
    public Map<String, Object> upload(@Upload(value = "123",limit = 1) List<User> users,
                                      @ErrorRows List<Row> rows,
                                      @ErrorRows List<ErrorRow> errorRows,
                                      @ErrorRows List<String> errorMessage,
                                      @ErrorRows Object errorObject,
                                      @ErrorRows Workbook workbook) throws IOException {
        Sheet sheetAt = workbook.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
        workbook.write(new FileOutputStream("错误.xlsx"));
        for (User user : users) {
            System.out.println(user);
        }
        Map<String, Object> result = new HashMap<>(16);
        result.put("data", users);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

}
