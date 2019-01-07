package com.quartz.monitor.orm.mybatis.excutor;


import com.quartz.monitor.model.AbstractMonitorDTO;
import com.quartz.monitor.model.MonitorDTO;
import com.quartz.monitor.model.postgresqlModel.PostgresqlDataSources;
import com.quartz.monitor.model.postgresqlModel.PostgresqlMonitorDTO;
import com.quartz.monitor.orm.mybatis.config.MappedStatement;
import com.quartz.monitor.orm.mybatis.util.ReflectMapperUtil;
import com.quartz.monitor.publisher.QuartzPostgresqlMonitor;
import com.quartz.monitor.util.connecPool.postgresql.ConnectProxy;
import com.quartz.monitor.util.connecPool.postgresql.PoolPostgresqlFactory;
import com.quartz.monitor.util.connecPool.postgresql.PostgresqlConPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * title:
 * create by tony_jaa
 * date 2019/1/6
 */
public class DefaultExcutor implements Excutor {
    private static Logger LOG = LogManager.getLogger( DefaultExcutor.class );

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) throws Exception{
        LOG.info("要查询的DTO对象parameter:"+parameter);

        if( !(parameter instanceof AbstractMonitorDTO) ) throw new RuntimeException("参数类型不对");
        AbstractMonitorDTO abstractMonitorDTO = (AbstractMonitorDTO) parameter;

        Object  dataSources  = ReflectMapperUtil.invokeGetField(abstractMonitorDTO,"dataSources");
        if( dataSources == null ) throw new RuntimeException("数据源为空");
        if( !(dataSources instanceof PostgresqlDataSources) ) throw new RuntimeException("数据源类型不对");


        Map<String,Object> map = abstractMonitorDTO.getParamMap();
        List<Map<String,Object>> list = this.queryDB( (PostgresqlDataSources) dataSources,abstractMonitorDTO.getParamMap(),ms );
        return (List<E>) list;
    }


    private List<Map<String, Object>> queryDB( PostgresqlDataSources dataSources,Map<String,Object> paraMap,MappedStatement ms ) throws Exception {
        LOG.info("要执行的sql语句为:"+ms.getSql());
        List<Map<String, Object>> list = new ArrayList<>();
        PostgresqlConPool postgresqlConPool = null;
        try {
            postgresqlConPool = PoolPostgresqlFactory.getConPoolInstance(dataSources);
            LOG.info("获取到的连接池：" + postgresqlConPool);
            dataSources = postgresqlConPool.getConnection();
            LOG.info("获取到的数据源：" + dataSources);
            String sql = ms.getSql();
            ConnectProxy connectProxy = dataSources.getConnectProxy();
            ResultSet rs = connectProxy.query(sql, getQueryArr(paraMap,ms.getParamSortMap()));
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

    private String[] getQueryArr( Map<String,Object> paraMap,Map<String, String> sortMap ){
        String[] arr = new String[]{};
        for(Map.Entry<String, String> entry : sortMap.entrySet()){
            String key =  String.valueOf(entry.getValue());
            arr[Integer.valueOf(entry.getKey())] = String.valueOf(paraMap.get(key));
        }
        if( arr.length == 0 ) return null;
        return arr;
    }

}
