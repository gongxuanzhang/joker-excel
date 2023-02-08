package org.gxz.joker.starter.exception;

/**
 *
 * 行移除异常
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class RowExceedException extends RuntimeException{


    public RowExceedException() {
    }

    public RowExceedException(String message) {
        super(message);
    }

    public RowExceedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RowExceedException(Throwable cause) {
        super(cause);
    }

    public RowExceedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
