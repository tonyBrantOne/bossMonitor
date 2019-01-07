package com.quartz.monitor.orm.mybatis.util;

import com.quartz.monitor.orm.mybatis.sqlSession.DefaultSqlSession;
import com.quartz.monitor.orm.mybatis.util.listen.MappedProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyMapperUtil {

    /**
     * 通class对象决定返回值类型
     * @param clazz
     * @param obj
     * @param <T>
     * @return
     */
    public static <T>T getInstance(Class<T> clazz, Object obj){
        InvocationHandler invocationHandler = new MappedProxy((DefaultSqlSession) obj);
        T helloProxy = (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                    new Class<?>[]{clazz},
                        invocationHandler);
        return helloProxy;
    }


}
