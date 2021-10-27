package org.gxz.joker.starter.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gxz.joker.starter.annotation.ErrorRows;
import org.gxz.joker.starter.annotation.Upload;
import org.gxz.joker.starter.tool.RowUtils;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author gxz gongxuanzhang@foxmail.com
 * 当需要错误解析的时候，可以将此内容加入到注解中
 **/
@Slf4j
public class ErrorRowsArgumentProcessor implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ErrorRows.class) &&
                (Workbook.class.isAssignableFrom(parameter.getParameterType()) ||
                        List.class == parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        ModelMap model = mavContainer.getModel();
        if (model.containsKey(ComponentConstant.ERROR_ROW_BINDER_KEY)) {
            List<Row> errorRows = (List<Row>) model.get(ComponentConstant.ERROR_ROW_BINDER_KEY);
            if (parameter.getParameterType() == List.class) {
                return errorRows;
            }
            if (Workbook.class == parameter.getParameterType()) {
                return errorWorkbook(model);
            }
        }
        throw new IllegalStateException(
                "不能解析@ErrorRows注解下的参数  @ErrorRows注解只能加在@Upload注解注释的参数之后，错误方法是" + parameter.getMethod());

    }

    private Workbook errorWorkbook(Map<String, Object> model) {
        Workbook workbook = new XSSFWorkbook();
        Sheet errorSheet = workbook.createSheet("错误数据");
        Row head = (Row) model.get(ComponentConstant.ERROR_HEAD_BINDER_KEY);
        List<Row> errorRows = (List<Row>) model.get(ComponentConstant.ERROR_ROW_BINDER_KEY);
        Row errorSheetHead = errorSheet.createRow(0);
        RowUtils.copyRow(head, errorSheetHead);
        for (int i = 0; i < errorRows.size(); i++) {
            Row row = errorSheet.createRow(i + 1);
            RowUtils.copyRow(errorRows.get(i), row);
        }
        return workbook;
    }


}
