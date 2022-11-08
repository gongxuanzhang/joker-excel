package org.gxz.joker.starter.component;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.gxz.joker.starter.annotation.DynamicSelect;
import org.gxz.joker.starter.element.ColChecker;
import org.gxz.joker.starter.element.FieldInfo;
import org.gxz.joker.starter.element.IndependentDynamic;
import org.gxz.joker.starter.element.MultiDynamic;
import org.gxz.joker.starter.exception.JokerRuntimeException;
import org.gxz.joker.starter.service.ColumnRule;
import org.gxz.joker.starter.tool.FormulaUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.gxz.joker.starter.tool.PoiUtils.getCellOrCreate;
import static org.gxz.joker.starter.tool.PoiUtils.getRowOrCreate;
import static org.gxz.joker.starter.tool.ValidationUtils.createColValidationFormula;

/**
 * 动态选择器
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class DynamicSelectSheetAppender implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final Map<Class<? extends DynamicSelector>, DynamicSelector> selectorMap = new HashMap<>();


    public void append(Workbook excel, Class<?> beanType, List<ColumnRule> rules) {
        DynamicSelect dynamicSelect = beanType.getAnnotation(DynamicSelect.class);
        if (dynamicSelect == null) {
            return;
        }
        DynamicSelector dynamicSelector = findSelector(dynamicSelect);
        FieldInfo[] infos = infoWrap(rules);
        DynamicReport dynamic = dynamicSelector.dynamic(infos);
        XSSFSheet dataSheet = (XSSFSheet) excel.getSheetAt(0);

        ColChecker rowChecker = new ColChecker();
        Sheet hideSheet = excel.createSheet(dynamicSelect.name());
        if (dynamicSelect.sheetHide()) {
            excel.setSheetHidden(excel.getSheetIndex(hideSheet), true);
        }

        int cellIndex = 0;
        Row firstRow = hideSheet.createRow(0);
        List<IndependentDynamic> independentDynamicList = dynamic.getIndependentDynamicList();
        //  独立动态设置
        if (!CollectionUtils.isEmpty(independentDynamicList)) {
            for (IndependentDynamic independentDynamic : independentDynamicList) {
                int infoIndex = independentDynamic.findInfoIndex(infos);
                rowChecker.check(infoIndex);
                String exportColumnName = infos[infoIndex].getExportColumnName();
                independentDynamic.fillCol(exportColumnName,cellIndex,hideSheet);
                //  设置数据校验
                String formula = FormulaUtils.columnNameFormula(hideSheet, cellIndex, independentDynamic.getItems().size());
                DataValidation validation = createColValidationFormula(dataSheet, infoIndex, formula);
                dataSheet.addValidationData(validation);
                cellIndex++;
            }
            cellIndex++;
        }


        List<MultiDynamic> multiDynamicList = dynamic.getMultiDynamicList();
        //   级联动态设置
        if (!CollectionUtils.isEmpty(multiDynamicList)) {
            for (MultiDynamic multiDynamic : multiDynamicList) {
                int parentIndex = multiDynamic.findInfoParentIndex(infos);
                int childIndex = multiDynamic.findInfoChildIndex(infos);
                rowChecker.check(parentIndex,childIndex);
                Map<String, List<String>> items = multiDynamic.getItems();
                //  父列的数据校验
                String formula = FormulaUtils.rowNameFormula(hideSheet, cellIndex, items.size());
                DataValidation validation = createColValidationFormula(dataSheet, parentIndex, formula);
                dataSheet.addValidationData(validation);
                for (Map.Entry<String, List<String>> stringListEntry : items.entrySet()) {
                    String key = stringListEntry.getKey();
                    Cell currentCell = getCellOrCreate(firstRow, cellIndex);
                    currentCell.setCellValue(key);
                    List<String> childNames = stringListEntry.getValue();
                    for (int i = 0; i < childNames.size(); i++) {
                        Row row = getRowOrCreate(hideSheet, i + 1);
                        Cell itemCell = getCellOrCreate(row, cellIndex);
                        itemCell.setCellValue(childNames.get(i));
                    }
                    Name name = excel.createName();
                    name.setNameName(key);
                    name.setRefersToFormula(FormulaUtils.columnNameFormula(hideSheet, cellIndex, childNames.size()));
                    cellIndex++;
                }
                //  子列的数据校验
                for (int i = 1; i < 2000; i++) {
                    String parentColName = CellReference.convertNumToColString(parentIndex);
                    String childFormula = String.format("INDIRECT(%s%s)", parentColName, (i + 1));
                    validation = createColValidationFormula(dataSheet, childIndex, childFormula);
                    dataSheet.addValidationData(validation);
                }

                // 中间空一行
                cellIndex++;
            }
        }

    }


    private FieldInfo[] infoWrap(List<ColumnRule> rules) {
        FieldInfo[] infos = new FieldInfo[rules.size()];
        for (int i = 0; i < rules.size(); i++) {
            ColumnRule columnRule = rules.get(i);
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setRawFieldName(columnRule.getDataRule().getFieldName());
            fieldInfo.setExportColumnName(columnRule.getHeadRule().getName());
            infos[i] = fieldInfo;
        }
        return infos;
    }

    private DynamicSelector findSelector(DynamicSelect dynamicSelect) {
        Class<? extends DynamicSelector> selector = dynamicSelect.selector();
        try {
            return applicationContext.getBean(selector);
        } catch (BeansException e) {
            return selectorMap.computeIfAbsent(selector, c -> {
                try {
                    return c.newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    throw new JokerRuntimeException("如果你想使用一个没有默认构造器的 DynamicSelector 请把他注入到Spring容器中");
                }
            });
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }
}
