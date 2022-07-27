package com.coder.common.util.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/***
 * 当一个类实现了这个接口（ApplicationContextAware）之后，Aware接口的Bean在被初始之后，可以取得一些相对应的资源，
 * 这个类可以直接获取spring 配置文件中 所有引用（注入）到的bean对象。
 */
@Slf4j
@Component
public final class SpringContextUtils implements BeanFactoryPostProcessor, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 私有化工具类的构造函数
     */
    private SpringContextUtils() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    /**
     * 根据名字获得spring context中的bean<br>
     *
     * @param name bean的名称
     * @return bean
     */
    public static Object getBean(String name) {

        return applicationContext.getBean(name);
    }

    /**
     * 根据类型获得spring context中的bean<br>
     *
     * @param requiredType bean的类型
     * @return bean
     */
    public static <T> T getBean(Class<T> requiredType) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(requiredType);
    }

    /**
     * 根据名称和类型获得spring context中的bean<br>
     *
     * @param name         bean 的名称
     * @param requiredType bean的类型
     * @return bean
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }
}
