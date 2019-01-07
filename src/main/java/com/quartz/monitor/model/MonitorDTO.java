package com.quartz.monitor.model;/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:10
 * @Description:
 */

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/27 20:10
 * @Description:
 */
public class MonitorDTO {
    protected Map<String, Object> paramMap = new HashMap<>();


    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
