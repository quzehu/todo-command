package com.quzehu.learn.interceptor;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.model.UserSession;
import com.quzehu.learn.utils.UserSessionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 命令拦截器
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.intercept.CommandInterceptor
 * @Author Qu.ZeHu
 * @Date 2021/1/6 11:10
 * @Version 1.0
 */

public class CommandInterceptor {

    @SuppressWarnings("unchecked")
    public <T extends Command> T createProxy(T proxied) {
        Class<?>[] interfaces = proxied.getClass().getInterfaces();
        DynamicProxyHandler<T> proxyHandler = new DynamicProxyHandler<>(proxied);
        return (T)Proxy.newProxyInstance(proxied.getClass().getClassLoader(), interfaces, proxyHandler);
    }


    private static class DynamicProxyHandler<T extends Command> implements InvocationHandler, Print {

        private final T proxied;

        public DynamicProxyHandler(T proxied) {
            this.proxied = proxied;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (UserSessionUtils.getUserBySession() == null) {
                println(StringConstant.PASSWORD_ERROR_PROMPT_CONSOLE);
                return null;
            }
            try {
                return method.invoke(proxied, args);
            }catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
    }
}
