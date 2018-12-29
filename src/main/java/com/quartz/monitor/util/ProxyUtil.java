package com.quartz.monitor.util;


import com.quartz.monitor.listen.ProxyInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyUtil {

    /**
     * 通class对象决定返回值类型
     * @param clazz
     * @param obj
     * @param <P>
     * @return
     */
    public static <P>P getInstance(Class<P> clazz, Object obj){

        InvocationHandler invocationHandler = new ProxyInvocationHandler(obj);
        P helloProxy = (P) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                    new Class<?>[]{clazz},
                        invocationHandler);
        return helloProxy;
    }


}
