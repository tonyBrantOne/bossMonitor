package com.quartz.monitor.util.connecPool.elasticsearch;

import com.quartz.monitor.model.esModel.EsDataSources;
import com.quartz.monitor.model.postgresqlModel.PostgresqlDataSources;
import com.quartz.monitor.util.connecPool.base.DefaultPoolFactory;
import com.quartz.monitor.util.connecPool.postgresql.PostgresqlConPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 13:28
 * @Description:
 */
public class ElasticsearchFactory extends DefaultPoolFactory{
    private static Logger LOG = LogManager.getLogger( ElasticsearchFactory.class );
    private static ConcurrentHashMap<Integer,ElasticsearchConPool> conPoolConcurrentHashMap = new ConcurrentHashMap<>();

    private ElasticsearchFactory() {};

    private static Object object = new Object();

    /**
     * 获取单列连接池，保证豪仔里只有一个游泳池
     * @return
     */
    public static ElasticsearchConPool getConPoolInstance(EsDataSources esDataSources) throws Exception {
        int hashCode =  esDataSources.getHost().hashCode();
        LOG.info("host:"+esDataSources.getHost()+",hashCode-------------"+hashCode);
        if ( null == conPoolConcurrentHashMap.get(hashCode) ) {
            synchronized ( object ) {
                if ( null == conPoolConcurrentHashMap.get(hashCode) ) {
                    LOG.info("hashCode："+hashCode+"+的连接池为空");
                    ElasticsearchConPool  elasticsearchConPool = new ElasticsearchConPool();//初始化连接池对象。
                    elasticsearchConPool.addDataSources(esDataSources);
                    conPoolConcurrentHashMap.put(hashCode,elasticsearchConPool);
                }
            }
        }
        LOG.info("hashCode-------------"+hashCode+",postgresqlConPool-------------"+conPoolConcurrentHashMap.get(hashCode).getLinkedBlockingQueue());
        return conPoolConcurrentHashMap.get(hashCode);
    }
}
