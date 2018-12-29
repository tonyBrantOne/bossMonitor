package com.quartz.monitor.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/29 12:35
 * @Description:
 */
public class DateUtil {
    public static final String MILLISECOND_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String HOUR_PATTERN = "yyyy-MM-dd HH";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String MINUTE_ONLY_PATTERN = "mm";
    public static final String HOUR_ONLY_PATTERN = "HH";

    public static String dateToStr(Date date, String formatStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( formatStr );
        return simpleDateFormat.format(date);
    }

}
