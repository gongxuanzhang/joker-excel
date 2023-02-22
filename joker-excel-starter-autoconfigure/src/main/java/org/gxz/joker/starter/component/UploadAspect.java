package org.gxz.joker.starter.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Aspect
public class UploadAspect {


    @Pointcut("@within(org.springframework.stereotype.Controller) && @args(org.gxz.joker.starter.annotation.Upload)")
    public void haveUpload() {
    }


    @Around("haveUpload()")
    public Object exportAspect(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }

}
