package org.gxz.joker.starter.component;

import lombok.Getter;
import org.gxz.joker.starter.element.IndependentDynamic;
import org.gxz.joker.starter.element.MultiDynamic;
import org.gxz.joker.starter.exception.DynamicException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 动态校验的返回值
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Getter
public class DynamicReport {

    private List<IndependentDynamic> independentDynamicList;

    private List<MultiDynamic> multiDynamicList;


    /**
     * 添加一个独立动态选择
     **/
    public DynamicReport appendIndependentDynamic(IndependentDynamic independentDynamic) {
        check(independentDynamic);
        independentDynamicList.add(independentDynamic);
        return this;
    }


    public DynamicReport appendMulitLevelDynamic(MultiDynamic multiDynamic) {
        check(multiDynamic);
        multiDynamicList.add(multiDynamic);
        return this;
    }


    private void check(IndependentDynamic independentDynamic) {
        String colName = independentDynamic.getColName();
        Integer index = independentDynamic.getIndex();
        List<String> items = independentDynamic.getItems();
        if (colName == null && index == null) {
            throw new DynamicException("independentDynamic的colName和index都没设置，需要设置一个");
        }
        if (CollectionUtils.isEmpty(items)) {
            throw new DynamicException("没有数据");
        }
        if (this.independentDynamicList == null) {
            this.independentDynamicList = new ArrayList<>();
        }
    }


    private void check(MultiDynamic multiDynamic) {
        if (multiDynamic.getParentColIndex() == null && multiDynamic.getParentColName() == null) {
            throw new DynamicException("MultiDynamic的parentColName和parentColIndex都没设置，需要设置一个");
        }
        if (multiDynamic.getChildColName() == null && multiDynamic.getChildColIndex() == null) {
            throw new DynamicException("MultiDynamic的childColName和childColIndex都没设置，需要设置一个");
        }
        Map<String, List<String>> items = multiDynamic.getItems();
        if (items.isEmpty()) {
            throw new DynamicException("没有数据");
        }

        if (this.multiDynamicList == null) {
            this.multiDynamicList = new ArrayList<>();
        }
    }

}
