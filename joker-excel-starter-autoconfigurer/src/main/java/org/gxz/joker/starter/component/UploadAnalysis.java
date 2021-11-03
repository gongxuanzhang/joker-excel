package org.gxz.joker.starter.component;

import org.gxz.joker.starter.annotation.Upload;
import org.gxz.joker.starter.service.UploadExcelReader;
import org.gxz.joker.starter.tool.ExcelExportExecutor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gxz.joker.starter.tool.ReflectUtil;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class UploadAnalysis {


    public ReadHolder<?> analysisData(MultipartFile file, MethodParameter parameter) {
        Class<?> genericClass = ReflectUtil.getOnlyGenericity(parameter);
        Assert.notNull(file, "找不到上传的文件");
        String fileName = file.getOriginalFilename();
        Assert.state(fileName.endsWith(".xlsx"), "文件格式错误 请下载数据修改后上传");
        try (InputStream fileInputStream = file.getInputStream()) {
            Upload upload = parameter.getParameterAnnotation(Upload.class);
            UploadExcelReader<?> uploadExcelReader = new UploadExcelReader<>(genericClass, upload);
            return uploadExcelReader.read(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}


