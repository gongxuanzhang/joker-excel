package org.gxz.joker.starter.component;

import org.gxz.joker.starter.annotation.EnableJokerExcel;
import org.gxz.joker.starter.config.JokerGlobalConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gongxuanzhang
 */
public class JokerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    Class<?>[] scanClass = new Class[]{JokerGlobalConfig.class, BaseUploadCheck.class};

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata,
                                        BeanDefinitionRegistry registry,
                                        BeanNameGenerator importBeanNameGenerator) {
        // 构建一个classPath扫描器
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.setResourceLoader(this.resourceLoader);
        for (Class<?> aClass : scanClass) {
            scanner.addIncludeFilter(new AssignableTypeFilter(aClass));
        }
        // 获取需要扫描的包路径
        List<String> basePackages = new ArrayList<>();
        Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableJokerExcel.class.getCanonicalName());
        if (!CollectionUtils.isEmpty(attributes)) {
            for (String pkg : (String[]) attributes.get("basePackages")) {
                if (StringUtils.hasText(pkg)) {
                    basePackages.add(pkg);
                }
            }
        }

        // 添加当前项目包
        basePackages.add(ClassUtils.getPackageName(metadata.getClassName()));
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
            // 构建信息
            if (!candidateComponents.isEmpty()) {
                for (BeanDefinition bd : candidateComponents) {
                    // 注入数据
                    String className = bd.getBeanClassName();
                    try {
                        // 这里如果直接使用Class.forName(className) 可能会找不到类
                        Class<?> clazz = resourceLoader.getClassLoader().loadClass(className);
                        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(clazz);
                        String beanId = importBeanNameGenerator.generateBeanName(bd, registry);

                        // 这里还可以设置依赖项，是否懒加载，构造方法参数等等与类定义有关的参数
                        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
                        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
                        beanDefinition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, className);
                        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, beanId);
                        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

}

