package cn.scu.imc.hiver.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.context = applicationContext;
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return context.getBeansOfType(type);
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static void getAllBean() {
        String[] beanNames = context.getBeanDefinitionNames();
        for(String beanName : beanNames){
            Object bean = context.getBean(beanName);
            System.out.println(bean);
        }
    }
}