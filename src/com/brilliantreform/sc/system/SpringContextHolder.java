package com.brilliantreform.sc.system;


import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringContextHolder implements ApplicationContextAware{

     private static ApplicationContext applicationContext;

     /*
      * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
      */
     public void setApplicationContext(ApplicationContext applicationContext) {
         SpringContextHolder.applicationContext = applicationContext;
     }

     /*
      * 取得存储在静态变量中的ApplicationContext.
      */
     public static ApplicationContext getApplicationContext() {
         return applicationContext;
     }

     /*
      * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
      */
     @SuppressWarnings("unchecked")
     public static <T> T getBean(String name) {
         return (T) applicationContext.getBean(name);
     }

     /*
      * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
      * 如果有多个Bean符合Class, 取出第一个.
      */
     @SuppressWarnings("unchecked")
     public static <T> T getBean(Class<T> clazz) {

         Map beanMaps = applicationContext.getBeansOfType(clazz);
         if (beanMaps!=null && !beanMaps.isEmpty()) {
             return (T) beanMaps.values().iterator().next();
         } else{
             return null;
         }
     }
 }