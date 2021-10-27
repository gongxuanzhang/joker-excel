package org.gxz.joker.starter.element;

import lombok.Getter;
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

    private String excelName;

    private String sheetName;

    private FieldHolder fieldHolder;

    private static final String EXCEL_SUFFIX = ".xlsx";

    /**
     * 合并excelNames
     **/
    public void fuseExcelName(ExcelNameOverlayable... element) {
        if (excelName != null) {
            throw new IllegalStateException("method fuseExcelName only use once");
        }
        this.excelName = FuseUtils.fuse(element);
        if (!StringUtils.hasText(excelName)) {
            excelName = DefaultValueConstant.EXPORT_EXCEL_NAME;
        }
        if (!excelName.endsWith(EXCEL_SUFFIX)) {
            excelName += EXCEL_SUFFIX;
        }
    }


    /**
     * 合并sheetNames
     **/
    public void fuseSheetName(SheetNameOverlayable... element) {
        if (sheetName != null) {
            throw new IllegalStateException("method fuseSheetName only use once");
        }
        this.sheetName = FuseUtils.fuse(element);
    }


    /**
     * 合并最后需要过滤的字段集合
     **/
    public void fuseField(FieldOverlayable... element) {
        if (fieldHolder != null) {
            throw new IllegalStateException("method fuseField only use once");
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
