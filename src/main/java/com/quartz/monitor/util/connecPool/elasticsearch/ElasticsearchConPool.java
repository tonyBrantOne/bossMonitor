package com.quartz.monitor.util.connecPool.elasticsearch;

import com.quartz.monitor.conf.excep.ConnectionRejectException;
import com.quartz.monitor.model.esModel.EsDataSources;
import com.quartz.monitor.model.postgresqlModel.PostgresqlDataSources;
import com.quartz.monitor.util.connecPool.base.ConPool;
import com.quartz.monitor.util.connecPool.postgresql.ConnectProxy;
import com.quartz.monitor.util.connecPool.postgresql.DBCon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 13:36
 * @Description:
 */
public class ElasticsearchConPool extends ConPool<EsDataSources,ConnectProxy> {

    private Logger LOG = LogManager.getLogger( ElasticsearchConPool.class );

    private LinkedBlockingQueue<EsDataSources> linkedBlockingQueue = new LinkedBlockingQueue<>(initialSize);

    private  AtomicInteger atomicInteger = new AtomicInteger(0);

    public LinkedBlockingQueue<EsDataSources> getLinkedBlockingQueue() {
        return linkedBlockingQueue;
    }

    public void setLinkedBlockingQueue(LinkedBlockingQueue<EsDataSources> linkedBlockingQueue) {
        this.linkedBlockingQueue = linkedBlockingQueue;
    }






    @Override
    protected void addDataSources(EsDataSources esDataSources) throws Exception{
        for (Integer integer = 0; integer < initialSize; integer++) {
                if( atomicInteger.incrementAndGet() == initialSize.intValue() ){
                    EsDataSources dataSources = esDataSources.clone( new EsDataSources());
                    HttpClientConnection connectProxy = null;
                    try {
                        connectProxy = new HttpClientConnection();
                        connectProxy.getCon(dataSources);
                    }catch ( RuntimeException e){
                        throw new ConnectionRejectException(e);
                    }
                    dataSources.setConnectProxy(connectProxy);
                    linkedBlockingQueue.offer(dataSources);
                }
        }
    }



    @Override
    public EsDataSources getConnection() throws Exception{
        return linkedBlockingQueue.take();
    }

    @Override
    public void destoryCon(EsDataSources dataSources) throws Exception{
        if( dataSources != null ) linkedBlockingQueue.offer(dataSources);
    }

}
