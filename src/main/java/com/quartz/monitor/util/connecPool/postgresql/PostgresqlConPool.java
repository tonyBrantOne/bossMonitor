package com.quartz.monitor.util.connecPool.postgresql;

import com.quartz.monitor.conf.excep.ConnectionRejectException;
import com.quartz.monitor.model.postgresqlModel.PostgresqlDataSources;
import com.quartz.monitor.publisher.QuartzPostgresqlMonitor;
import com.quartz.monitor.util.connecPool.base.ConPool;
import org.apache.ibatis.jdbc.Null;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 13:36
 * @Description:
 */
public class PostgresqlConPool extends ConPool<PostgresqlDataSources,ConnectProxy> {

    private Logger LOG = LogManager.getLogger( PostgresqlConPool.class );

    private LinkedBlockingQueue<PostgresqlDataSources> linkedBlockingQueue = new LinkedBlockingQueue<>(initialSize);

    private  AtomicInteger atomicInteger = new AtomicInteger(0);





    @Override
    protected void addDataSources(PostgresqlDataSources postgresqlDataSources) throws Exception{
        for (Integer integer = 0; integer < initialSize; integer++) {
                if( atomicInteger.incrementAndGet() == initialSize.intValue() ){
                    PostgresqlDataSources dataSources = postgresqlDataSources.clone( new PostgresqlDataSources());
                //    Connection con = null;
                    ConnectProxy connectProxy = null;
                    try {
                     //   con = DBCon.getCon(dataSources);
                        connectProxy = new ConnectProxy();
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
    public PostgresqlDataSources getConnection() throws Exception{
        PostgresqlDataSources dataSources = linkedBlockingQueue.take();
        return dataSources;
    }

    @Override
    public void destoryCon(PostgresqlDataSources dataSources) throws Exception{
        if( dataSources != null ) {
            dataSources.getConnectProxy().closeAll();
            linkedBlockingQueue.offer(dataSources);
        }
    }

    public void destoryCon(PostgresqlDataSources dataSources, PreparedStatement ps, ResultSet rs) throws Exception {
        DBCon.closeAll(ps,rs);
        if( dataSources != null ) linkedBlockingQueue.offer(dataSources);
    }

    public LinkedBlockingQueue<PostgresqlDataSources> getLinkedBlockingQueue() {
        return linkedBlockingQueue;
    }

    public void setLinkedBlockingQueue(LinkedBlockingQueue<PostgresqlDataSources> linkedBlockingQueue) {
        this.linkedBlockingQueue = linkedBlockingQueue;
    }
}
