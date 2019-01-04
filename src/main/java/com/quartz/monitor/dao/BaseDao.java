package com.quartz.monitor.dao;

import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorDTO;

import java.util.List;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/4 16:28
 * @Description:
 */
public interface BaseDao<T> {

    public List<Map<String,Object>> selectCurrentConnections(T t ) throws Exception;

    public List<Map<String,Object>> selectMaxConnections(T t ) throws Exception;

}
