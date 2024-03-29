package org.gxz.joker.starter.element;

import lombok.Getter;
import lombok.Setter;
import org.gxz.joker.starter.expression.ConcatPropertyResolver;
import org.gxz.joker.starter.tool.FuseUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;


/**
 * 一个通过融合配置之后确定的excel的各个属性
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/

@Getter
public class ExcelDescription {


    private final ConcatPropertyResolver resolver;

    public ExcelDescription(ConcatPropertyResolver resolver) {
        this.resolver = resolver;
    }


    private String excelName;

    private String sheetName;

    /**
     * 字段的取舍相关
     **/
    private FieldHolder fieldHolder;

    @Setter
    private Class<?> beanType;

    private static final String EXCEL_SUFFIX = ".xlsx";

    /**
     * 合并excelNames
     **/
    public void fuseExcelName(ExcelNameOverlayable... element) {
        if (excelName != null) {
            throw new IllegalStateException("method fuseExcelName only invoke once");
        }
        this.excelName = FuseUtils.fuse(element);
        if (!StringUtils.hasText(excelName)) {
            excelName = DefaultValueConstant.EXPORT_EXCEL_NAME;
        }
        if (!excelName.endsWith(EXCEL_SUFFIX)) {
            excelName += EXCEL_SUFFIX;
        }
        excelName = resolver.resolvePlaceholders(excelName);
    }


    /**
     * 合并sheetNames
     **/
    public void fuseSheetName(SheetNameOverlayable... element) {
        if (sheetName != null) {
            throw new IllegalStateException("method fuseSheetName only invoke once");
        }
        this.sheetName = FuseUtils.fuse(element);
    }


    /**
     * 合并最后需要过滤的字段集合
     **/
    public void fuseField(FieldOverlayable... element) {
        if (fieldHolder != null) {
            throw new IllegalStateException("method fuseField only invoke once");
        }
        Arrays.sort(element);
        for (FieldOverlayable fieldOverlayable : element) {
            FieldHolder value = fieldOverlayable.getValue();
            if (!value.isEmpty()) {
                this.fieldHolder = value;
                break;
            }
        }
        if (fieldHolder == null) {
            fieldHolder = new FieldHolder().setIgnore(new String[]{}).setInclude(new String[]{});
        }

    }

}
