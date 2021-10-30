package org.gxz.joker.starter.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gxz.joker.starter.annotation.ErrorRows;
import org.gxz.joker.starter.annotation.Upload;
import org.gxz.joker.starter.service.ErrorRow;
import org.gxz.joker.starter.tool.ReflectUtil;
import org.gxz.joker.starter.tool.RowUtils;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 当需要错误解析的时候，可以将此内容加入到注解中
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Slf4j
public class ErrorRowsArgumentProcessor implements HandlerMethodArgumentResolver {

    private final Class<?>[] SUPPORT_TYPE = new Class[]{Workbook.class, List.class, Object.class};

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (!parameter.hasParameterAnnotation(ErrorRows.class)) {
            return false;
        }
        Class<?> parameterType = parameter.getParameterType();
        for (Class<?> support : SUPPORT_TYPE) {
            if (parameterType == support) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        ModelMap model = mavContainer.getModel();
        if (model.containsKey(ComponentConstant.ERROR_ROW_BINDER_KEY)) {
            Class<?> parameterType = parameter.getParameterType();
            if (parameterType == Workbook.class) {
                ErrorRows errorRows = parameter.getParameterAnnotation(ErrorRows.class);
                return createErrorExcel(model,errorRows.head());
            }
            if (parameterType == List.class || parameterType == Object.class) {
                return selectListResult(ReflectUtil.getOnlyGenericity(parameter), model);
            }
        }
        throw new IllegalStateException(
                "不能解析@ErrorRows注解下的参数  @ErrorRows注解只能加在@Upload注解注释的参数之后，错误方法是" + parameter.getMethod());

    }


    private Object selectListResult(Class<?> genericity, ModelMap model) {
        List<ErrorRow> errorRows = (List<ErrorRow>) model.get(ComponentConstant.ERROR_ROW_BINDER_KEY);
        if (genericity == Row.class) {
            return errorRows.stream().map(ErrorRow::getRow).collect(Collectors.toList());
        }
        if (genericity == ErrorRow.class) {
            return errorRows;
        }
        if (genericity == Object.class) {
            return errorRows;
        }
        if (genericity == String.class) {
            return errorRows.stream().map(ErrorRow::getErrorMessage).collect(Collectors.toList());
        }
        throw new IllegalArgumentException("不支持的泛型");

    }

    private Workbook createErrorExcel(Map<String, Object> model,String headRow) {
        Workbook workbook = new XSSFWorkbook();
        Sheet errorSheet = workbook.createSheet("错误数据");
        Row head = (Row) model.get(ComponentConstant.ERROR_HEAD_BINDER_KEY);
        List<ErrorRow> errorRows = (List<ErrorRow>) model.get(ComponentConstant.ERROR_ROW_BINDER_KEY);
        Row errorSheetHead = errorSheet.createRow(0);
        RowUtils.copyRow(head, errorSheetHead);
        RowUtils.appendCell(errorSheetHead,headRow);
        for (int i = 0; i < errorRows.size(); i++) {
            Row row = errorSheet.createRow(i + 1);
            ErrorRow holder = errorRows.get(i);
            RowUtils.copyRow(holder.getRow(), row);
            RowUtils.appendCell(row,holder.getErrorMessage(),head.getLastCellNum());
        }
        return workbook;
    }


}
