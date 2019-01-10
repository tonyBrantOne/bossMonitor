package com.quartz.monitor.util.connecPool.redis;

import com.quartz.monitor.conf.excep.ConnectionRejectException;
import com.quartz.monitor.model.postgresqlModel.PostgresqlDataSources;
import com.quartz.monitor.model.redisModel.RedisDataSources;
import com.quartz.monitor.util.connecPool.base.DefaultPoolFactory;
import com.quartz.monitor.util.connecPool.postgresql.PostgresqlConPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 13:28
 * @Description:
 */
public class PoolRedisFactory extends DefaultPoolFactory<RedisDataSources>{
    private static Logger LOG = LogManager.getLogger( PoolRedisFactory.class );
    private static ConcurrentHashMap<Integer,JedisPool> conPoolConcurrentHashMap = new ConcurrentHashMap<>();

    private PoolRedisFactory() {};

    private static Object object = new Object();

    /**
     * 获取单列连接池，保证豪仔里只有一个游泳池
     * @return
     */
    public static JedisPool getConPoolInstance(RedisDataSources redisDataSources) throws Exception {
        int hashCode =  redisDataSources.getHost().hashCode();
        LOG.info("host:"+redisDataSources.getHost()+",hashCode-------------"+hashCode);
        if ( null == conPoolConcurrentHashMap.get(hashCode) ) {
            synchronized ( object ) {
                if ( null == conPoolConcurrentHashMap.get(hashCode) ) {
                    LOG.info("hashCode："+hashCode+"+的连接池为空");
                    JedisPoolConfig jedisPoolConfig = getJedisPoolConfig();
                    try{
                        JedisPool jedisPool = new JedisPool(jedisPoolConfig,redisDataSources.getHost().split(":")[0],Integer.valueOf(redisDataSources.getHost().split(":")[1]));
                        conPoolConcurrentHashMap.put(hashCode,jedisPool);
                    }catch ( Exception e ){
                        throw new ConnectionRejectException(e);
                    }
                }
            }
        }
        LOG.info("hashCode-------------"+hashCode+",postgresqlConPool-------------"+conPoolConcurrentHashMap.get(hashCode).getNumActive());
        return conPoolConcurrentHashMap.get(hashCode);
    }

    /**
     * jedis连接池配置
     * @return
     */
    private static JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        /**
         * 最多
         */
        jedisPoolConfig.setMaxTotal(1);
        /**
         * 控制一个pool最多有多少个状态为idle空闲的jedis实例。
         */
        jedisPoolConfig.setMaxIdle(1);
        /**
         * 借实例时，最大等待时长。超过这个时间抛出异常。
         */
        jedisPoolConfig.setMaxWaitMillis(100*1000);
        /**
         * 获取jedis实例时，是否检查连接的可用性。
         */
        jedisPoolConfig.setTestOnBorrow(true);
        return jedisPoolConfig;
    }

    public static void relaseCon(JedisPool jedisPool, Jedis jedis) throws Exception{
            if( null != jedis ) jedisPool.returnResourceObject(jedis);
    }

}
