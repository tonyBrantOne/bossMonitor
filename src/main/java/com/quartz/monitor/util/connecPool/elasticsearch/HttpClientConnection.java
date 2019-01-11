package com.quartz.monitor.util.connecPool.elasticsearch;/**
 * @Auther: tony_jaa
 * @Date: 2019/1/11 11:53
 * @Description:
 */

import com.quartz.monitor.conf.excep.ConnectionRejectException;
import com.quartz.monitor.model.esModel.EsDataSources;
import com.quartz.monitor.model.postgresqlModel.PostgresqlDataSources;
import com.quartz.monitor.model.redisModel.RedisDataSources;
import com.quartz.monitor.util.connecPool.postgresql.DBCon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2019/1/11 11:53
 * @Description:http连接代理类
 */
public class HttpClientConnection {

    private static Logger logger = LogManager.getLogger( HttpClientConnection.class );
    private String url;
    private RestTemplate restTemplate = null;

    /**
     * @return返回数据库连接对象
     */
    public RestTemplate getCon( EsDataSources dataSources ){
        try {
            url =  new StringBuilder("http://").append(dataSources.getHost()).toString();
            restTemplate = new RestTemplate();
        } catch (Exception e) {
            throw new ConnectionRejectException(e);
        }
        return restTemplate;
    }


    /**
     *
     * @param sql
     * @param pras
     * @return
     * @throws Exception
     */
    public  Map<String,Object> query(String sql, String... pras ) throws Exception{
        try {
            Map<String,Object> rs = restTemplate.getForObject(url,Map.class,pras == null? new HashMap<>():pras[0]);
            return rs;
        }catch ( Exception e ){
            throw new ConnectionRejectException(e);
        }finally {

        }
    }

    /**
     *
     * @param
     * @param pras
     * @return
     * @throws Exception
     */
    public  Map<String,Object> query( Map... pras ) throws Exception{
        try {
            Map<String,Object> map = new HashMap<>();
            if( !(pras == null || pras.length == 0) ) map = pras[0];
            Map<String,Object> rs = restTemplate.getForObject(url,Map.class,map);
            return rs;
        }catch ( Exception e ){
            throw new ConnectionRejectException(e);
        }finally {

        }
    }

}
