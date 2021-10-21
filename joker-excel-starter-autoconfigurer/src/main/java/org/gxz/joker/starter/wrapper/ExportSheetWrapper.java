package org.gxz.joker.starter.wrapper;

import org.gxz.joker.starter.annotation.Export;
import org.gxz.joker.starter.element.OrderConstant;
import org.gxz.joker.starter.element.SheetNameOverlayable;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ExportSheetWrapper implements SheetNameOverlayable {


    private final Export export;

    public ExportSheetWrapper(Export export){
        this.export = export;
    }

    @Override
    public int getOrder() {
        return OrderConstant.EXPORT_ANNOTATION_ORDER;
    }

    @Override
    public String getValue() {
        if(export ==null){
           return defaultValue();
        }
        return export.sheetName();
    }
}
