package org.gxz.joker.starter.component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class UploadContextHolder {

    private final static ThreadLocal<UploadContext> context = new ThreadLocal<>();

    public static void setContext(UploadContext uploadContext) {
        context.set(uploadContext);
    }

    public static void clean() {
        context.remove();
    }
}
