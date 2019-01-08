package com.quartz.monitor.orm.mybatis.sqlBuilder;

import com.quartz.monitor.util.RegularUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/8 11:12
 * @Description:
 */
public class XMLStatementBuilder {

    public Map<String,String> getParamSort( String sql ){
        Map<String,String> map = new LinkedHashMap<>();
        List<String> list = RegularUtil.obtainMatchArr( RegularUtil.POUND_QUESTION,sql);
        for (int i = 0; i < list.size(); i++) {
            map.put( "" + (i + 1) , list.get(i) );
        }
        return map;
    }

}
