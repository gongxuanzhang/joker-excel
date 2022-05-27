package org.gxz.joker.starter.config.build;

import org.apache.poi.ss.usermodel.Sheet;
import org.gxz.joker.starter.component.BaseUploadCheck;
import org.gxz.joker.starter.config.ExcelFieldDescription;
import org.gxz.joker.starter.element.check.CheckComposite;
import org.gxz.joker.starter.element.gardener.GardenerComposite;
import org.gxz.joker.starter.exception.CheckValueException;
import org.gxz.joker.starter.expression.JokerExpressionParser;
import org.gxz.joker.starter.service.ColumnRule;

import java.util.Collections;
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

    public static void check(ColumnRule rule, Object value) throws CheckValueException {
        checkComposite.check(rule, value);
    }


    /**
     * 注册构建
     **/
    public static void registerBuild(JokerBuilder jokerBuilder) {
        registerHeadBuilder(jokerBuilder.head());
        registerExpression(jokerBuilder.expressionBuilder);
    }

    /**
     * 构建校验信息
     **/
    public static void registerCheck(BaseUploadCheck baseUploadCheck) {
        if (checkMap == null) {
            checkMap = new HashMap<>(16);
        }
        checkMap.put(baseUploadCheck.getId(), baseUploadCheck);
    }

    /**
     * 构建表达式信息
     **/
    private static void registerExpression(ExpressionBuilder expressionBuilder) {
        parserList = expressionBuilder.getParserList();
    }


    /**
     * 构建表头信息
     **/
    private static void registerHeadBuilder(HeadBuilder headBuilder) {
        registerPrefix(headBuilder);
        registerSuffix(headBuilder);

    }



    /**
     * 构建前缀
     **/
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

    /**
     * 构建后缀
     **/
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


    /**
     * 通过id拿到具体的校验类
     *
     * @param checkId id
     * @return 校验类
     **/
    public static BaseUploadCheck getUploadChecker(String checkId) {
        if (checkMap == null) {
            return null;
        }
        return checkMap.get(checkId);
    }

    /**
     * 特殊样式的添加
     *
     * @param sheet    sheet
     * @param ruleList 规则内容
     **/
    public static void clip(Sheet sheet, List<ColumnRule> ruleList) {
        gardenerComposite.clip(sheet, ruleList);
    }

    /**
     * 支持的特殊表达式解析器
     *
     * @return 返回个啥
     **/
    public static List<JokerExpressionParser> getParserList() {
        if (parserList == null) {
            parserList = Collections.emptyList();
        }
        return parserList;
    }
}
