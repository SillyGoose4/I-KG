package cn.nnu.jyjs.knowledgegraph.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class Beans implements ApplicationContextAware {
    private static ApplicationContext context;
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Bean
    public static ResourceLoader createResourceLoader() {
        return new DefaultResourceLoader();
    }
}
