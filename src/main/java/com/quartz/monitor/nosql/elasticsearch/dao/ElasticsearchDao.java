package com.quartz.monitor.nosql.elasticsearch.dao;

import com.quartz.monitor.model.esModel.EsMonitorDTO;
import com.quartz.monitor.model.redisModel.RedisMonitorDTO;

import java.util.Map;

/**
 * title:
 * create by tony_jaa
 * date 2019/1/6
 */
public interface ElasticsearchDao {

    /**
     * 测试es的心跳
     * @param esMonitorDTO
     * @return
     */
    Map<String, Object> ping(EsMonitorDTO esMonitorDTO) throws Exception;

}
