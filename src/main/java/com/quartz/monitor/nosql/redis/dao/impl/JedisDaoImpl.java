package com.quartz.monitor.nosql.redis.dao.impl;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/10 20:07
 * @Description:
 */

import com.quartz.monitor.conf.excep.ConnectionRejectException;
import com.quartz.monitor.model.redisModel.RedisMonitorDTO;
import com.quartz.monitor.nosql.redis.dao.JedisDao;
import com.quartz.monitor.util.connecPool.redis.PoolRedisFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/10 20:07
 * @Description:
 */

@Component
public class JedisDaoImpl implements JedisDao {

    private Logger logger = LogManager.getLogger(JedisDaoImpl.class);

    @Override
    public String ping(RedisMonitorDTO redisMonitorDTO) throws Exception {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
             jedisPool = PoolRedisFactory.getConPoolInstance(redisMonitorDTO.getDataSources());
             logger.info("当前连接池对象："+ jedisPool +"，获取到的数据源：" + redisMonitorDTO.getDataSources());
             jedis = jedisPool.getResource();
             return jedis.ping();
        } catch (Exception e) {
            throw new ConnectionRejectException(e);
        }finally {
            PoolRedisFactory.relaseCon(jedisPool,jedis);
        }
    }



}
