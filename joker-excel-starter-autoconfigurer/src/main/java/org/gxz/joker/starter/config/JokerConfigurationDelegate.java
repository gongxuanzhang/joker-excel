package org.gxz.joker.starter.config;

import org.gxz.joker.starter.config.build.ConcatSupplier;
import org.gxz.joker.starter.config.build.HeadBuilder;
import org.gxz.joker.starter.config.build.JokerBuilder;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerConfigurationDelegate {

    private static ConcatSupplier suffixSupplier;
    private static ConcatSupplier prefixSupplier;

    private JokerConfigurationDelegate() {

    }

    public static void registerBuild(JokerBuilder jokerBuilder) {
        HeadBuilder head = jokerBuilder.head();
        registerPrefix(head);
        registerSuffix(head);
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

        if (head.getPrefix() != null) {
            suffixSupplier = (d) -> head.getSuffix();
            return;
        }
        if (head.getPrefixSupplier() != null) {
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


}
