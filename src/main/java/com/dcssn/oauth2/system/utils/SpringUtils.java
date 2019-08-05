package com.dcssn.oauth2.system.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * spring 工具类
 *
 * @author lihy
 * @date 2019-5-31
 */
@Component
public final class SpringUtils implements BeanFactoryPostProcessor
{

    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        SpringUtils.beanFactory = beanFactory;
    }


    /**
     * 返回唯一匹配给定对象类型的bean实例
     *
     * @param clz bean类型；可以是接口或超类
     * @return 匹配所需类型的单个bean的实例
     */
    public static <T> T getBean(Class<T> clz)
    {
        return (T) beanFactory.getBean(clz);
    }


}
