package org.gxz.joker.starter.exception;

/**
 * joker顶层异常
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerException extends Exception {

    public JokerException() {

    }

    public JokerException(String message) {
        super(message);
    }

    public JokerException(String message, Throwable cause) {
        super(message, cause);
    }

    public JokerException(Throwable cause) {
        super(cause);
    }

    public JokerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
