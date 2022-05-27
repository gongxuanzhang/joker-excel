package org.gxz.joker.starter.exception;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class JokerRuntimeException extends RuntimeException {

    public JokerRuntimeException() {
    }

    public JokerRuntimeException(String message) {
        super(message);
    }

    public JokerRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JokerRuntimeException(Throwable cause) {
        super(cause);
    }

    public JokerRuntimeException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
