package org.gxz.joker.starter.config;

import org.apache.poi.ss.usermodel.Row;
import org.gxz.joker.starter.component.UploadAnalysisPostProcessor;
import org.gxz.joker.starter.config.build.CheckBuilder;
import org.gxz.joker.starter.config.build.ConcatSupplier;
import org.gxz.joker.starter.config.build.HeadBuilder;
import org.gxz.joker.starter.config.build.JokerBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerConfigurationDelegate {

    private static ConcatSupplier suffixSupplier;
    private static ConcatSupplier prefixSupplier;
    private static List<UploadAnalysisPostProcessor> processors;

    private JokerConfigurationDelegate() {

    }

    public static void registerBuild(JokerBuilder jokerBuilder) {
        HeadBuilder head = jokerBuilder.head();
        registerPrefix(head);
        registerSuffix(head);
        CheckBuilder check = jokerBuilder.check();
        registerCheck(check);

    }


    private static void registerCheck(CheckBuilder checkBuilder) {
        if (checkBuilder == null) {
            registerNullCheck();
            return;
        }
        processors = checkBuilder.getProcessors();
    }

    private static void registerNullCheck() {
        processors = new ArrayList<>();
        processors.add((data, row) -> {});
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

    public static void postUploadAnalysis(List<?> data, List<Row> rows) {
        processors.forEach((p) -> p.postProcessAfterUploadAnalysis(data, rows));
    }


}
