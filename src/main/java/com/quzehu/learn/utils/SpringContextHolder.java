package com.quzehu.learn.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Lazy(false)
@Order(1)
@Slf4j
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

   private static ApplicationContext applicationContext = null;

   public static ApplicationContext getApplicationContext() {
       assertContextInjected();
       return applicationContext;
   }


   @SuppressWarnings("unchecked")
   public static <T> T getBean(String name) {
       assertContextInjected();
       return (T) applicationContext.getBean(name);
   }


   public static <T> T getBean(Class<T> requiredType) {
       assertContextInjected();

       return applicationContext.getBean(requiredType);
   }

   public static <T> T getBean(Class<T> requiredType, Object ...args) {
       assertContextInjected();
       return applicationContext.getBean(requiredType, args);
   }


   public static void clearHolder() {
       if (log.isDebugEnabled()) {
           log.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
       }
       applicationContext = null;
   }

   @Override
   public void setApplicationContext(ApplicationContext applicationContext) {
       SpringContextHolder.applicationContext = applicationContext;
   }
   @Override
   public void destroy() throws Exception {
       SpringContextHolder.clearHolder();
   }


   private static void assertContextInjected() {
       Validate.validState(applicationContext != null,
               "applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
   }
}