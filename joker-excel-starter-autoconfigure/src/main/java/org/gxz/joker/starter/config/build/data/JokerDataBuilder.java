package org.gxz.joker.starter.config.build.data;

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.function.Predicate;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerDataBuilder {

    public FieldBuilder name(String name) {
        return new FieldBuilder(name);

    }


    public static class FieldBuilder {
        private final String name;
        private CellStyle headStyle;


        public FieldBuilder(String name) {
            this.name = name;
        }

        public FieldBuilder setHeadStyle(CellStyle style) {
            return this;
        }

        public FieldBuilder filter(Predicate<Object> filter) {
            return this;
        }

        public FieldBuilder setCellStyle() {
            return this;
        }
    }
}
