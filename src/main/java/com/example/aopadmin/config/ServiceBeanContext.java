package com.example.aopadmin.config;

import org.springframework.context.ApplicationContext;

/**
 * @description
 * @author: wannggp
 * @create: 2020-10-13 17:20
 **/
public class ServiceBeanContext {

    private static volatile ApplicationContext ctx ;

    public static void load(ApplicationContext applicationContext){
        ctx = applicationContext;
    }


    public static <T> T getBean(Class<T> className) {
        return (T) ctx.getBean(className);
    }

}
