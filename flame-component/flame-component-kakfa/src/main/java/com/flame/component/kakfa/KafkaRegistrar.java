package com.flame.component.kakfa;

import com.flame.component.kakfa.consumer.CommonConsumer;
import com.flame.component.kakfa.consumer.ConsumerInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author king
 * @date 2022年01月14日 14:51
 * @DOTO:
 */
@Slf4j
public class KafkaRegistrar implements ImportBeanDefinitionRegistrar, BeanClassLoaderAware,
        EnvironmentAware, ResourceLoaderAware {

    private ClassLoader classLoader;
    private Environment environment;
    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        String packageName = ClassUtils.getPackageName(annotationMetadata.getClassName());

        ClassPathScanningCandidateComponentProvider scanner = getScanner();

        scanner.setEnvironment(environment);
        scanner.setResourceLoader(resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(com.flame.component.kakfa.ConsumerTag.class));

        Set<BeanDefinition> components = scanner.findCandidateComponents(packageName);

        log.info("[kafka] find kafka consumers: {}",components.size());

        for (BeanDefinition beanDefinition : components) {
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
                String className = annotatedBeanDefinition.getMetadata().getClassName();
                log.info("[kafka] definiting consumer: {}",className);
                Class<?> beanClass = null;
                try {
                    beanClass = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    log.error("registerBeanDefinitions error");
                }
                if (beanClass != null && isImpl(beanClass)) {
                    registryBean(beanDefinitionRegistry,beanClass);
                }
            }
        }

    }

    private void registryBean(BeanDefinitionRegistry registry,
                              Class<?> beanClazz) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        String beanId = StringUtils.uncapitalize(beanClazz.getSimpleName());
        registry.registerBeanDefinition(beanId, definition);
        ConsumerInitializer.addConsumer(beanClazz);
        log.info("[kafka] injected kafka consumer to spring ioc:{}",beanClazz);
    }

    private boolean isImpl(Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        while (superclass != null && superclass != Object.class) {
            if (superclass == CommonConsumer.class) {
                return true;
            }
            superclass = superclass.getSuperclass();
        }
        return false;
    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {

            @Override
            protected boolean isCandidateComponent(
                    AnnotatedBeanDefinition beanDefinition) {
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (beanDefinition.getMetadata().isInterface()
                            && beanDefinition.getMetadata()
                            .getInterfaceNames().length == 1
                            && Annotation.class.getName().equals(beanDefinition
                            .getMetadata().getInterfaceNames()[0])) {
                        try {
                            Class<?> target = ClassUtils.forName(
                                    beanDefinition.getMetadata().getClassName(),
                                    KafkaRegistrar.this.classLoader);
                            return !target.isAnnotation();
                        } catch (Exception ex) {
                            log.error(
                                    "Could not load target class: "
                                            + beanDefinition.getMetadata().getClassName(),
                                    ex);
                        }
                    }
                    return true;
                }
                return false;

            }
        };
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
