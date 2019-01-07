package com.quartz.monitor.util.connecPool.postgresql;

import com.quartz.monitor.model.postgresqlModel.PostgresqlDataSources;
import com.quartz.monitor.publisher.QuartzPostgresqlMonitor;
import com.quartz.monitor.util.connecPool.base.ConPool;
import com.quartz.monitor.util.connecPool.base.DefaultPoolFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 13:28
 * @Description:
 */
public class PoolPostgresqlFactory  extends DefaultPoolFactory{
    private static Logger LOG = LogManager.getLogger( PoolPostgresqlFactory.class );
    private static ConcurrentHashMap<Integer,PostgresqlConPool> conPoolConcurrentHashMap = new ConcurrentHashMap<>();

 //   private static PostgresqlConPool postgresqlConPool = null;

    private PoolPostgresqlFactory() {};

    private static Object object = new Object();

    /**
     * 获取单列连接池，保证豪仔里只有一个游泳池
     * @return
     */
    public static PostgresqlConPool getConPoolInstance(PostgresqlDataSources postgresqlDataSources) throws Exception {
        int hashCode =  postgresqlDataSources.getHost().hashCode();
        LOG.info("host:"+postgresqlDataSources.getHost()+",hashCode-------------"+hashCode);
        if ( null == conPoolConcurrentHashMap.get(hashCode) ) {
            synchronized ( object ) {
                if ( null == conPoolConcurrentHashMap.get(hashCode) ) {
                    LOG.info("hashCode："+hashCode+"+的连接池为空");
                    PostgresqlConPool  postgresqlConPool = new PostgresqlConPool();//初始化连接池对象。
                    postgresqlConPool.addDataSources(postgresqlDataSources);
                    conPoolConcurrentHashMap.put(hashCode,postgresqlConPool);
                }
            }
        }
        LOG.info("hashCode-------------"+hashCode+",postgresqlConPool-------------"+conPoolConcurrentHashMap.get(hashCode).getLinkedBlockingQueue());
        return conPoolConcurrentHashMap.get(hashCode);
    }
}
