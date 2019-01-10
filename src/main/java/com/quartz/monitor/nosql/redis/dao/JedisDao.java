package com.quartz.monitor.nosql.redis.dao;

import com.quartz.monitor.conf.excep.ConnectionRejectException;
import com.quartz.monitor.model.redisModel.RedisMonitorDTO;

import java.util.List;
import java.util.Map;

/**
 * title:
 * create by tony_jaa
 * date 2019/1/6
 */
public interface JedisDao {

    /**
     * 测试redis的心跳
     * @param redisMonitorDTO
     * @return
     */
    String ping(RedisMonitorDTO redisMonitorDTO) throws Exception;

}
