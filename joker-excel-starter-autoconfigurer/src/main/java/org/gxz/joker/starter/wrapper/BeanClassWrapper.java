package org.gxz.joker.starter.wrapper;

import org.gxz.joker.starter.annotation.ExcelData;
import org.gxz.joker.starter.element.FieldOverlayable;
import org.gxz.joker.starter.element.FieldHolder;
import org.gxz.joker.starter.element.OrderConstant;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class BeanClassWrapper implements FieldOverlayable {

    final ExcelData excelData;


    public BeanClassWrapper(Class<?> clazz) {
        this.excelData = clazz.getAnnotation(ExcelData.class);
    }


    @Override
    public int getOrder() {
        return OrderConstant.BEAN_CLASS_ORDER;
    }

    @Override
    public FieldHolder getValue() {
        if (excelData == null) {
            return defaultValue();
        }
        return new FieldHolder().setAllField(excelData.allField())
                .setInclude(excelData.include())
                .setIgnore(excelData.ignore());
    }
}
