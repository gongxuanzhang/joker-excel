package org.gxz.joker.starter.config.build;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gongxuanzhang
 */
@Getter
public class BodyBuilder extends JoinAbleBuilder {

    private final List<WhenClassBuilder<?>> whenBuilderList = new ArrayList<>();

    private WhenClassBuilder<?> currentBuilder;


    public BodyBuilder(JokerBuilder jokerBuilder) {
        super(jokerBuilder);
    }

    /**
     *
     * 定义导出类型在什么情况下的设置
     * @param beanType 实体类类型
     * @return 返回个啥
     **/
    public WhenClassBuilder<?> whenClass(Class<?> beanType) {
        if(this.currentBuilder!=null){
            this.whenBuilderList.add(currentBuilder);
        }
        return new WhenClassBuilder<>(beanType);
    }





}
