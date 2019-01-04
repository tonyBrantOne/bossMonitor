package com.quartz.monitor.util.connecPool.base;

import com.quartz.monitor.util.connecPool.postgresql.PoolPostgresqlFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 13:23
 * @Description:
 */
public abstract class DefaultPoolFactory<T> {

    /**
     * 获取单列连接池，保证豪仔里只有一个游泳池
     * @return
     */
//    public static ConPool getConPoolInstance( DefaultPoolFactory defaultPoolFactory ) {
//        if (null == defaultPoolFactory.conPool) {
//            synchronized (JedisPool.class) {
//                if (null == defaultPoolFactory.conPool) {
//
//                }
//            }
//        }
//        return defaultPoolFactory.conPool;
//    }
}
