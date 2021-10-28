package org.gxz.joker.starter.element.gardener;

import org.apache.poi.ss.usermodel.Sheet;
import org.gxz.joker.starter.tool.Rule;
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
public class GardenerComposite implements Gardener, ApplicationContextAware {


    private List<Gardener> gardeners = new LinkedList<>();


    public GardenerComposite addResolver(Gardener resolver) {
        this.gardeners.add(resolver);
        return this;
    }

    public GardenerComposite addResolvers(Gardener... resolvers) {
        if (resolvers != null) {
            Collections.addAll(this.gardeners, resolvers);
        }
        return this;
    }

    public GardenerComposite addResolvers(List<? extends Gardener> resolvers) {
        if (resolvers != null) {
            this.gardeners.addAll(resolvers);
        }
        return this;
    }

    public List<Gardener> getResolvers() {
        return Collections.unmodifiableList(this.gardeners);
    }

    @Override
    public void clip(Sheet sheet, List<Rule> ruleList) {
        for (Gardener gardener : this.gardeners) {
            if (gardener.support(sheet,ruleList)) {
                gardener.clip(sheet,ruleList);
            }
        }
    }

    @Override
    public boolean support(Sheet sheet, List<Rule> ruleList) {
        return true;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Gardener> beansOfType = applicationContext.getBeansOfType(Gardener.class);
        for (Gardener value : beansOfType.values()) {
            if(!(value instanceof GardenerComposite)){
                addResolver(value);
            }
        }
    }
}
