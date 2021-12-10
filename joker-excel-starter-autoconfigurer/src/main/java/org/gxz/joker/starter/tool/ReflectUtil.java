package org.gxz.joker.starter.tool;

import lombok.Data;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Method;


/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
public class ReflectUtil {

    private ReflectUtil() {

    }

    public static Class<?> getOnlyGenericity(MethodParameter parameter) {
        ResolvableType resolvableType = ResolvableType.forType(parameter.getGenericParameterType());
        ResolvableType[] generics = resolvableType.getGenerics();
        if (generics.length == 0) {
            return Object.class;
        }
        return generics[0].getRawClass();
    }


    public static Class<?> getMethodResultGenericity(Method method) {
        ResolvableType resolvableType = ResolvableType.forMethodReturnType(method);
        ResolvableType[] generics = resolvableType.getGenerics();
        if (generics.length == 0) {
            return null;
        }
        return generics[0].getRawClass();
    }

}
