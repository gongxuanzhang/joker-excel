package org.gxz.joker.starter.wrapper;

import org.gxz.joker.starter.annotation.Export;
import org.gxz.joker.starter.element.FieldOverlayable;
import org.gxz.joker.starter.element.FieldHolder;
import org.gxz.joker.starter.element.OrderConstant;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ExportFieldWrapper implements FieldOverlayable {

    private final Export export;

    public ExportFieldWrapper(Export export) {
        this.export = export;
    }

    @Override
    public int getOrder() {
        return OrderConstant.EXPORT_ANNOTATION_ORDER;
    }


    @Override
    public FieldHolder getValue() {
        if (export == null) {
            return defaultValue();
        }
        return new FieldHolder().setIgnore(export.ignore()).setInclude(export.include())
                .setAllField(export.allField());
    }
}
