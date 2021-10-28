package org.gxz.joker.starter.config;

import org.apache.poi.ss.formula.functions.T;
import org.gxz.joker.starter.component.BaseUploadCheck;
import org.gxz.joker.starter.component.UploadCheck;
import org.gxz.joker.starter.config.build.ConcatSupplier;
import org.gxz.joker.starter.config.build.HeadBuilder;
import org.gxz.joker.starter.config.build.JokerBuilder;
import org.gxz.joker.starter.exception.ConvertException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerConfigurationDelegate {

    private static ConcatSupplier suffixSupplier;
    private static ConcatSupplier prefixSupplier;
    private static Map<String, BaseUploadCheck> checkMap;

    private JokerConfigurationDelegate() {

    }

    public static void registerBuild(JokerBuilder jokerBuilder) {
        HeadBuilder head = jokerBuilder.head();
        registerPrefix(head);
        registerSuffix(head);
    }


    public static void registerCheck(BaseUploadCheck baseUploadCheck) {
        if (checkMap == null) {
            checkMap = new HashMap<>(16);
        }
        checkMap.put(baseUploadCheck.getId(), baseUploadCheck);
    }

    private static void registerPrefix(HeadBuilder head) {
        if (head == null) {
            prefixSupplier = (d) -> "";
            return;
        }

        if (head.getPrefix() != null) {
            prefixSupplier = (d) -> head.getPrefix();
            return;
        }
        if (head.getPrefixSupplier() != null) {
            prefixSupplier = head.getPrefixSupplier();
        }
        if (prefixSupplier == null) {
            prefixSupplier = (d) -> "";
        }

    }

    private static void registerSuffix(HeadBuilder head) {
        if (head == null) {
            suffixSupplier = (d) -> "";
            return;
        }

        if (head.getSuffix() != null) {
            suffixSupplier = (d) -> head.getSuffix();
            return;
        }
        if (head.getSuffixSupplier() != null) {
            suffixSupplier = head.getSuffixSupplier();
        }
        if (suffixSupplier == null) {
            suffixSupplier = (d) -> "";
        }
    }

    public static String getPrefix(ExcelFieldDescription description) {
        return prefixSupplier.apply(description);
    }

    public static String getSuffix(ExcelFieldDescription description) {
        return suffixSupplier.apply(description);
    }


    public static <T> BaseUploadCheck uploadCheck(String checkId) {
        if (checkMap == null) {
            return null;
        }
        return checkMap.get(checkId);
    }
}
