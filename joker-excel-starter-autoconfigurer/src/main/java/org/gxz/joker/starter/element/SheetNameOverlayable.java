package org.gxz.joker.starter.element;


/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface SheetNameOverlayable extends StringOverlayable {


    /**
     * 提供默认的值
     **/
    @Override
    default String defaultValue() {
        return DefaultValueConstant.SHEET_EXCEL_NAME;
    }


}
