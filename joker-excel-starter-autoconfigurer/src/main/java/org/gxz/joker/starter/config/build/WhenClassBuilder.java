package org.gxz.joker.starter.config.build;

import java.util.function.Predicate;

/**
 * 当指定class的时候时刻，
 * 专用于配置建造
 * @author gongxuanzhang
 */
 class WhenClassBuilder<T> {

        final Class<T> currentClass;
        Predicate<T> filter;

        public WhenClassBuilder(Class<T> currentClass) {
            this.currentClass = currentClass;
        }

        /**
         *
         * 当一行如何的时候，配置一行数据
         * @param filter 行过滤
         * @return builder
         **/
        public WhenClassBuilder<T> whenRow(Predicate<T> filter){
            if(this.filter == null){
                this.filter = filter;
            }else{
                this.filter = this.filter.and(filter);
            }
            return this;
        }

    }
