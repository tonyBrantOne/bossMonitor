package com.quartz.monitor.util;/**
 * @Auther: tony_jaa
 * @Date: 2018/8/22 16:08
 * @Description:
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quartz.monitor.model.MonitorDTO;
import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: tony_jaa
 * @Date: 2018/8/22 16:08
 * @Description:
 */
public class FastJsonListObjectUtil {





//    public static  List<JSONObject> toJsonListJSONObject(List<User> list ){
//        List<JSONObject> list1 = new ArrayList<JSONObject>();
//        for( User user : list ){
//            String str = JSON.toJSONString(user);
//            list1.add(JSONObject.parseObject(str));
//        }
//        return list1;
//    }




    /**
     *
     * @param list
     * @return
     */
    public static <T> String toJsonStr(List<T> list,Class<T> clazz){
        String str = JSONArray.toJSONString(list);
        return str;
    }

    /**
     *
     */
    public static <T> List<T> toListObject( String str, Class<T> clazz){
        List<T> list = new ArrayList<>();
        if( StringUtils.isNotBlank(str) ) list = JSONObject.parseArray(str,clazz);
        return  list;
    }

    public static  List<JSONObject> toListJSONObject(String str){
        List<JSONObject> list = new ArrayList<>();
        if( StringUtils.isNotBlank(str) ) list = JSONObject.parseArray(str,JSONObject.class);
        return  list;
    }
}
