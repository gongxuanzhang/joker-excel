package org.gxz.joker.starter.tool;

import lombok.Data;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


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
        Type genericReturnType = method.getGenericReturnType();
        //  获取返回值的泛型参数
        if (genericReturnType instanceof ParameterizedType) {
            Type actualTypeArgument = ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
            return (Class<?>) actualTypeArgument;
        }
        return null;
    }

}
