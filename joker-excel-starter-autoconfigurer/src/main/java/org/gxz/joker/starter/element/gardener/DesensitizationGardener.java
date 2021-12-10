package org.gxz.joker.starter.element.gardener;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.gxz.joker.starter.config.ExcelFieldDescription;
import org.gxz.joker.starter.service.ColumnRule;
import org.gxz.joker.starter.service.DataRule;
import org.gxz.joker.starter.tool.PoiUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * 脱敏内容输出
 * 规则1:    1_3
 * 保留前后字符,  1_3  前面保留多少位 后面保留多少位
 * 1_3    123456789 -->  1******789
 * 规则2：    3~@
 * 保留前3位 和@之后的
 * 514190950@foxmail.com ---> 514******@foxmail.com
 * 支持多个字符  例如 3~abc   如果单元格不包含字符串，将只保留前面几位
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class DesensitizationGardener extends XSSFGardener {


    @Override
    public void xssfClip(XSSFSheet sheet, List<ColumnRule> ruleList) {
        Function<String, String>[] functions = new Function[ruleList.size()];
        for (int i = 0; i < functions.length; i++) {
            functions[i] = Optional.ofNullable(ruleList.get(i))
                    .map(ColumnRule::getDataRule)
                    .map(DataRule::getExcelFieldDescription)
                    .map(ExcelFieldDescription::getDesensitizationExpression)
                    .map(this::analysis)
                    .orElse(null);
        }
        for (int i = 0; i < functions.length; i++) {
            if (functions[i] != null) {
                for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    XSSFRow row = sheet.getRow(rowIndex);
                    XSSFCell cell = row.getCell(i);
                    Object cellValue = PoiUtils.getCellValue(cell);
                    if (cellValue != null) {
                        String apply = functions[i].apply(cellValue.toString());
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(apply);
                    }
                }
            }
        }

    }


    private Function<String, String> analysis(String expression) {
        if (!StringUtils.hasText(expression)) {
            return null;
        }
        try {
            if (expression.contains("_")) {
                String[] s = expression.split("_");
                int retainLeft = Integer.parseInt(s[0]);
                int retainRight = Integer.parseInt(s[1]);
                return retainAround(retainLeft, retainRight);
            }
            if (expression.contains("~")) {
                String[] s = expression.split("~");
                int retainLeft = Integer.parseInt(s[0]);
                String wall = s[1];
                return retainWall(retainLeft, wall);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private Function<String, String> retainWall(int retainLeft, String wall) {
        return (str) -> {
            if (StringUtils.isEmpty(str)) {
                return str;
            }
            int i = str.indexOf(wall);
            if (i == -1) {
                if (retainLeft >= str.length()) {
                    return str;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str, 0, retainLeft);
                for (int i1 = 0; i1 < str.length() - retainLeft; i1++) {
                    sb.append("*");
                }
                return sb.toString();
            } else if (i <= retainLeft) {
                return str;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(str, 0, retainLeft);
                for (int i1 = 0; i1 < i - retainLeft - 1; i1++) {
                    sb.append("*");
                }
                sb.append(str, i, str.length());
                return sb.toString();
            }
        };
    }

    private Function<String, String> retainAround(int retainLeft, int retainRight) {
        return (str) -> {
            if (StringUtils.hasText(str) && str.length() > retainLeft + retainRight) {
                StringBuilder sb = new StringBuilder();
                sb.append(str, 0, retainLeft);
                for (int i = 0; i < str.length() - retainLeft - retainRight; i++) {
                    sb.append("*");
                }
                sb.append(str.substring(str.length() - retainRight));
                return sb.toString();
            }
            return str;
        };
    }


}
