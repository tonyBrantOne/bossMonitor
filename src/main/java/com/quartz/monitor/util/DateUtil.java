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

    /**
     * 获取日期格式化字符串
     * */
    public static String getFormatStr(Date date,String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * 根据日期返回yyyyMM格式字符串
     * */
    public static String getYearMonth(Date date) {
        return getFormatStr(date,"yyyyMM");
    }

    /**
     * 根据时间获取ES的表名
     * @param createTimeBegin
     * @param createTimeEnd
     * @return
     */
    public static String getEsTableNameByDate(  String tableName,Date createTimeBegin , Date createTimeEnd){
        String beginStrTime = getYearMonth(createTimeBegin);
        String endStrTime = getYearMonth(createTimeEnd);
        StringBuilder stringBuilder = new StringBuilder( tableName ).append( beginStrTime );
        if( beginStrTime.equals(endStrTime) ) return stringBuilder.toString();
        return stringBuilder.append(",").append( tableName ).append(endStrTime).toString();
    }

}
