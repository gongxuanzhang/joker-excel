package org.gxz.joker.starter.config.build;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class JoinAbleBuilder {

    protected final JokerBuilder jokerBuilder;

    public JoinAbleBuilder(JokerBuilder jokerBuilder) {
        this.jokerBuilder = jokerBuilder;
    }

    public JokerBuilder and() {
        return this.jokerBuilder;
    }

}
