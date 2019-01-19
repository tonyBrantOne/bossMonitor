package com.quartz.monitor.util;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/19 13:18
 * @Description:
 */

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/19 13:18
 * @Description:
 */
public class StringUtil {

    public static String firstPhareToBig( String name ){
        return name.substring(0,1).toUpperCase() + name.substring(1,name.length());
    }

}
