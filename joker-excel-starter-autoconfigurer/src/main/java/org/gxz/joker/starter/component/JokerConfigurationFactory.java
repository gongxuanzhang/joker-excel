package org.gxz.joker.starter.component;

/**
 * factory to joker configuration
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface JokerConfigurationFactory {


    JokerConfiguration getJokerConfiguration();

    JokerConfiguration getJokerConfiguration(String name);

    JokerConfiguration getJokerConfiguration(Class<?> clazz);


}
