package com.tincery.starter.tool;


import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author: gxz gongxuanzhang@foxmail.com
 **/
public class ExportUtils {

    private ExportUtils() {
        throw new RuntimeException("工具类禁止创建对象");
    }

    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            // throw new NormalException(e.getMessage());
        }
    }

}
