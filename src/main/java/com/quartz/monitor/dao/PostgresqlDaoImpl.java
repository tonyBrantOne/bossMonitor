package com.quartz.monitor.dao;

import com.quartz.monitor.conf.excep.ConnectionRejectException;
import com.quartz.monitor.model.postgresqlModel.PostgresqlDataSources;
import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorDTO;
import com.quartz.monitor.util.connecPool.postgresql.ConnectProxy;
import com.quartz.monitor.util.connecPool.postgresql.DBCon;
import com.quartz.monitor.util.connecPool.postgresql.PoolPostgresqlFactory;
import com.quartz.monitor.util.connecPool.postgresql.PostgresqlConPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/3 18:35
 * @Description:
 */

public class PostgresqlDaoImpl implements PostgresqlDao{
    private Logger LOG = LogManager.getLogger( PostgresqlDaoImpl.class );

    @Override
    public List<Map<String, Object>> selectCurrentConnections(PostgresqlMonitorDTO postgresqlMonitorDTO) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        PostgresqlConPool postgresqlConPool = null;
        PostgresqlDataSources dataSources = null;
        try {
            postgresqlConPool = PoolPostgresqlFactory.getConPoolInstance(postgresqlMonitorDTO.getDataSources());
            LOG.info("获取到的连接池：" + postgresqlConPool);
            dataSources = postgresqlConPool.getConnection();
            LOG.info("获取到的数据源：" + dataSources);
            String sql = "select count(1) as current_connections from pg_stat_activity";
            ConnectProxy connectProxy = dataSources.getConnectProxy();
                ResultSet rs = connectProxy.query(sql, null);
                ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
                int columnCount = md.getColumnCount();   //获得列数
                while (rs.next()) {
                    Map<String, Object> rowData = new HashMap<String, Object>(columnCount);
                    for (int i = 1; i <= columnCount; i++) {
                        rowData.put(md.getColumnName(i), rs.getObject(i));
                    }
                    list.add(rowData);
                }
                return list;
        } finally {
            if( null != postgresqlConPool) postgresqlConPool.destoryCon(dataSources);
        }
    }

    @Override
    public List<Map<String, Object>> selectMaxConnections(PostgresqlMonitorDTO postgresqlMonitorDTO) throws Exception {
        return null;
    }


}
