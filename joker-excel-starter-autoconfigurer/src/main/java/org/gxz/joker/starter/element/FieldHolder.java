package org.gxz.joker.starter.element;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Setter
@Getter
public class FieldHolder {

    private String[] include;
    private String[] ignore;
    private boolean allField = DefaultValueConstant.ALL_FIELD;


    public boolean isEmpty() {
        return (include == null || include.length == 0) &&
                (ignore == null || ignore.length == 0);
    }

}
