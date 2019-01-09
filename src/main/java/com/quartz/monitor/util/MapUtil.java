package com.quartz.monitor.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/9 13:15
 * @Description:
 */
public class MapUtil {

    public static Map<String,Object> getMapByArr( String[] strArr, Object[] objects ){
        Map<String,Object> map = new HashMap<>();
        for (int i = 0; i < strArr.length; i++) {
            String key = strArr[i];
            Object v = objects[i];
            map.put(key,v);
        }
        return map;
    }
}
