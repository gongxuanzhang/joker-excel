package org.gxz.joker.starter.element.check;

import org.gxz.joker.starter.exception.CheckValueException;
import org.gxz.joker.starter.service.ColumnRule;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class CheckComposite implements RuleCheck, ApplicationContextAware {


    private List<RuleCheck> checks = new LinkedList<>();


    public CheckComposite addCheck(RuleCheck check) {
        this.checks.add(check);
        return this;
    }

    public CheckComposite addChecks(RuleCheck... check) {
        if (check != null) {
            Collections.addAll(this.checks, check);
        }
        return this;
    }

    public CheckComposite addChecks(List<? extends RuleCheck> check) {
        if (check != null) {
            this.checks.addAll(check);
        }
        return this;
    }

    public List<RuleCheck> getResolvers() {
        return Collections.unmodifiableList(this.checks);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RuleCheck> beansOfType = applicationContext.getBeansOfType(RuleCheck.class);
        for (RuleCheck value : beansOfType.values()) {
            if (!(value instanceof CheckComposite)) {
                addCheck(value);
            }
        }
    }

    @Override
    public void check(ColumnRule rule, Object value) throws CheckValueException {
        for (RuleCheck check : this.checks) {
            check.check(rule, value);
        }
    }
}
