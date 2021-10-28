package org.gxz.joker.starter.config;

import org.apache.poi.ss.usermodel.Sheet;
import org.gxz.joker.starter.component.BaseUploadCheck;
import org.gxz.joker.starter.config.build.ConcatSupplier;
import org.gxz.joker.starter.config.build.HeadBuilder;
import org.gxz.joker.starter.config.build.JokerBuilder;
import org.gxz.joker.starter.element.gardener.GardenerComposite;
import org.gxz.joker.starter.tool.Rule;

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
    private static GardenerComposite gardenerComposite;

    private JokerConfigurationDelegate() {

    }

    public static void registerGardener(GardenerComposite gardenerComposite){
        if(JokerConfigurationDelegate.gardenerComposite!=null){
            throw new IllegalArgumentException("重复注册 gardenerComposite");
        }
        JokerConfigurationDelegate.gardenerComposite = gardenerComposite;
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
        if(prefixSupplier == null){
            prefixSupplier = (d) -> "";
        }
        return prefixSupplier.apply(description);
    }

    public static String getSuffix(ExcelFieldDescription description) {
        if(suffixSupplier == null){
            suffixSupplier = (d) -> "";
        }
        return suffixSupplier.apply(description);
    }


    public static BaseUploadCheck uploadCheck(String checkId) {
        if (checkMap == null) {
            return null;
        }
        return checkMap.get(checkId);
    }

    public static void clip(Sheet sheet, List<Rule> ruleList){
        gardenerComposite.clip(sheet,ruleList);
    }
}
