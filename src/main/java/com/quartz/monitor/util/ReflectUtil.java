package com.quartz.monitor.util;/**
 * @Auther: tony_jaa
 * @Date: 2018/12/10 20:39
 * @Description:
 */



import com.quartz.monitor.model.MonitorDTO;

import java.lang.reflect.Method;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/10 20:39
 * @Description:
 */
public class ReflectUtil {


    public static Object invoke(Object object, String methodName, Object baseDTO ) throws Exception {
        try {
            Method method = object.getClass().getMethod(methodName,baseDTO.getClass());
            Object result = method.invoke(object,baseDTO);
            return result;
        }catch ( Exception e){
            System.out.println(e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
