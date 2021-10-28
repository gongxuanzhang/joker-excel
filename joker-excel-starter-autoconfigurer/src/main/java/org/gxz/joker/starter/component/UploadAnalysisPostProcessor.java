package org.gxz.joker.starter.component;

import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 *
 * 解析成功之后的 切面
 * @author gxz gongxuanzhang@foxmail.com
 **/
@FunctionalInterface
public interface UploadAnalysisPostProcessor {

    void postProcessAfterUploadAnalysis(List<?> data, List<Row> rows);


}
