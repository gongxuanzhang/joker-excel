package org.gxz.joker.starter.config.build;

import org.gxz.joker.starter.config.ExcelFieldDescription;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@FunctionalInterface
public interface ConcatSupplier {

    String apply(ExcelFieldDescription description);

}
