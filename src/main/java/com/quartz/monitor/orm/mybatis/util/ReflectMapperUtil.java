package com.quartz.monitor.orm.mybatis.util;/**
 * @Auther: tony_jaa
 * @Date: 2018/12/10 20:39
 * @Description:
 */



import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/10 20:39
 * @Description:
 */
public class ReflectMapperUtil {


    public static Object invokeGetField(Object object, String filedName ) throws Exception {
        Field privateStringField = object.getClass().getDeclaredField(filedName);
        privateStringField.setAccessible(true);
        Object fieldValue =  privateStringField.get(object);
        return fieldValue;
    }
}
