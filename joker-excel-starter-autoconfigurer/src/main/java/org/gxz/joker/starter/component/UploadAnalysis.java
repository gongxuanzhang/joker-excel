package org.gxz.joker.starter.component;

import org.gxz.joker.starter.tool.ExcelExportExecutor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class UploadAnalysis {


    public List<?> analysisData(MultipartFile file, MethodParameter parameter) {
        ParameterizedType parameterizedType = (ParameterizedType) parameter.getParameter().getParameterizedType();
        Class<?> genericClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        Assert.notNull(file, "找不到上传的文件");
        String fileName = file.getOriginalFilename();
        Assert.state(fileName.endsWith(".xlsx"), "文件格式错误 请下载数据修改后上传");
        try (InputStream fileInputStream = file.getInputStream()) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
            return ExcelExportExecutor.readWorkBook(xssfWorkbook, genericClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}


