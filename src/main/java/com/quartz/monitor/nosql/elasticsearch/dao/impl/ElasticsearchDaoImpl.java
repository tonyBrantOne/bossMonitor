package com.quartz.monitor.nosql.elasticsearch.dao.impl;
/**
 * @Auther: tony_jaa
 * @Date: 2019/1/10 20:07
 * @Description:
 */

import com.quartz.monitor.conf.excep.ConnectionRejectException;
import com.quartz.monitor.model.esModel.EsDataSources;
import com.quartz.monitor.model.esModel.EsMonitorDTO;
import com.quartz.monitor.model.redisModel.RedisMonitorDTO;
import com.quartz.monitor.nosql.elasticsearch.dao.ElasticsearchDao;
import com.quartz.monitor.nosql.redis.dao.JedisDao;
import com.quartz.monitor.util.connecPool.elasticsearch.ElasticsearchConPool;
import com.quartz.monitor.util.connecPool.elasticsearch.ElasticsearchFactory;
import com.quartz.monitor.util.connecPool.elasticsearch.HttpClientConnection;
import com.quartz.monitor.util.connecPool.redis.PoolRedisFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/10 20:07
 * @Description:
 */

@Component
public class ElasticsearchDaoImpl implements ElasticsearchDao {

    private Logger logger = LogManager.getLogger(ElasticsearchDaoImpl.class);

    @Override
    public Map<String, Object> ping(EsMonitorDTO esMonitorDTO) throws Exception {
        ElasticsearchConPool pool = null;
        HttpClientConnection httpClientConnection = null;
        EsDataSources esDataSources = null;
        try {
                pool = ElasticsearchFactory.getConPoolInstance(esMonitorDTO.getDataSources());
                logger.info("当前连接池对象："+ pool +"，获取到的数据源：" + esMonitorDTO.getDataSources());
                esDataSources = pool.getConnection();
                httpClientConnection =  esDataSources.getConnectProxy();
                Map<String, Object> result = httpClientConnection.query(new HashMap[]{});
                return result;
        }finally {
            if( null != pool) pool.destoryCon(esDataSources);
        }
    }



}
