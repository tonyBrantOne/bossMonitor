package com.quartz.monitor.nosql.es.dao;

import java.util.List;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/8 20:40
 * @Description:
 */
public interface EsMonitorDao {

    int insert(Map map) throws Exception;

    List<Map<String,Object>> selectList(Map map);
}
