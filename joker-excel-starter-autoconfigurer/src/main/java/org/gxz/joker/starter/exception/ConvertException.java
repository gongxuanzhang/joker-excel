package org.gxz.joker.starter.exception;


import org.gxz.joker.starter.convert.Converter;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 抛出这个异常说明在excel convert解析的过程中出现了问题
 **/
public class ConvertException extends JokerException {


    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(Converter<?> converter, String cellValue, Class<?> fieldType) {
        super(clacMessage(converter, cellValue, fieldType));
    }

    protected static String clacMessage(Converter<?> converter, String cellValue, Class<?> fieldType) {
        return "解析器[" + converter.getClass() + "]尝试解析[" + cellValue + "]为" + fieldType.getName() + "发生了异常";
    }


}
