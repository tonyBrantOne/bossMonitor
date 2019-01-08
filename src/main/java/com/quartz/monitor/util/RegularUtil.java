package com.quartz.monitor.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/8 11:13
 * @Description:
 */
public class RegularUtil {

    public static final String POUND_QUESTION = "#\\{([^\\}]+)\\}";

    public static final String QUESTION = "#\\{(\\S*)}";


    public static List<String> obtainMatchArr( String reg, String str ){
        List<String> listStr = new ArrayList<>();
        Pattern pattern = Pattern.compile( reg );
        Matcher matcher = pattern.matcher(str);
        while ( matcher.find() ){
            listStr.add(matcher.group(1).trim());
        }
        return listStr;
    }

}
