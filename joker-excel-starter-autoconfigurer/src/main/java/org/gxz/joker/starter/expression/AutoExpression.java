package org.gxz.joker.starter.expression;


import org.springframework.expression.EvaluationException;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * @date 2021/10/31 17:55
 */
public class AutoExpression extends JokerArgumentExpression {

    public AtomicInteger count = new AtomicInteger(0);

    public AtomicLong timeStamp = new AtomicLong();

    public final int numberFill;



    public AutoExpression(String expression, String[] args) {
        super(expression, args);
    }


    @Override
    protected void initArgs(String[] args) {
        if(args == null || args.length == 0){
            numberFill = 0;
        }else{
            args.
        }
    }

    @Override
    public Object getValue() throws EvaluationException {
        return null;
    }
}
