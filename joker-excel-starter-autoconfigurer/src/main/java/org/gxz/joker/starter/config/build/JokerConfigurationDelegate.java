package org.gxz.joker.starter.config.build;

import org.apache.poi.ss.usermodel.Sheet;
import org.gxz.joker.starter.component.BaseUploadCheck;
import org.gxz.joker.starter.config.ExcelFieldDescription;
import org.gxz.joker.starter.element.check.CheckComposite;
import org.gxz.joker.starter.element.gardener.GardenerComposite;
import org.gxz.joker.starter.exception.CellValueException;
import org.gxz.joker.starter.expression.JokerExpressionParser;
import org.gxz.joker.starter.service.Rule;

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
    private static CheckComposite checkComposite;
    private static List<JokerExpressionParser> parserList;
    private JokerConfigurationDelegate() {

    }

    public static void registerGardener(GardenerComposite gardenerComposite) {
        if (JokerConfigurationDelegate.gardenerComposite != null) {
            throw new IllegalArgumentException("重复注册 gardenerComposite");
        }
        JokerConfigurationDelegate.gardenerComposite = gardenerComposite;
    }

    public static void registerCheck(CheckComposite checkComposite) {
        if (JokerConfigurationDelegate.checkComposite != null) {
            throw new IllegalArgumentException("重复注册 gardenerComposite");
        }
        JokerConfigurationDelegate.checkComposite = checkComposite;
    }

    public static void check(Rule rule, Object value) throws CellValueException {
        checkComposite.check(rule, value);
    }


    public static void registerBuild(JokerBuilder jokerBuilder) {
        HeadBuilder head = jokerBuilder.head();
        registerPrefix(head);
        registerSuffix(head);
        registerParser(jokerBuilder.expressionBuilder);
    }


    public static void registerCheck(BaseUploadCheck baseUploadCheck) {
        if (checkMap == null) {
            checkMap = new HashMap<>(16);
        }
        checkMap.put(baseUploadCheck.getId(), baseUploadCheck);
    }


    public static void registerParser(ExpressionBuilder expressionBuilder){
        parserList = expressionBuilder.getParserList();
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
        if (prefixSupplier == null) {
            prefixSupplier = (d) -> "";
        }
        return prefixSupplier.apply(description);
    }

    public static String getSuffix(ExcelFieldDescription description) {
        if (suffixSupplier == null) {
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

    public static void clip(Sheet sheet, List<Rule> ruleList) {
        gardenerComposite.clip(sheet, ruleList);
    }

    public static List<JokerExpressionParser> getParserList(){
        return parserList;
    }
}
