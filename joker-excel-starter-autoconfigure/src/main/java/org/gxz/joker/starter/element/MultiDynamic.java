package org.gxz.joker.starter.element;

import lombok.Data;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 独立列的动态选择框
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
public class MultiDynamic {

    /**
     * 父列名
     **/
    private String parentColName;

    /**
     * 子列名
     **/
    private String childColName;

    /**
     * 父列号  列号优先级高
     **/
    private Integer parentColIndex;

    /**
     * 子列号 列号优先级高
     **/
    private Integer childColIndex;

    /**
     * 父列对应的子类的数据
     **/
    private final Map<String, List<String>> items = new LinkedHashMap<>();


    public MultiDynamic putItem(String parentItem, List<String> childItem) {
        items.computeIfAbsent(parentItem, k -> new ArrayList<>()).addAll(childItem);
        return this;
    }


   public int findInfoParentIndex(FieldInfo[] infos) {
        if (this.parentColIndex != null) {
            Assert.isTrue(this.parentColIndex < infos.length , "index大于列数");
            return this.parentColIndex;
        }
        for (int i = 0; i < infos.length; i++) {
            if (Objects.equals(parentColName, infos[i].getExportColumnName())) {
                return i;
            }
        }
        throw new IllegalArgumentException("找不到对应列");
    }

   public int findInfoChildIndex(FieldInfo[] infos) {
        if (this.childColIndex != null) {
            Assert.isTrue(this.childColIndex < infos.length, "index大于列数");
            return this.childColIndex;
        }
        for (int i = 0; i < infos.length; i++) {
            if (Objects.equals(childColName, infos[i].getExportColumnName())) {
                return i;
            }
        }
        throw new IllegalArgumentException("找不到对应列");
    }

}
