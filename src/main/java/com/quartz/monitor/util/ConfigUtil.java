package com.quartz.monitor.util;

import com.quartz.monitor.conf.ConstantParam;
import com.quartz.monitor.conf.DataSourcesAll;
import com.quartz.monitor.model.DateSources;
import com.quartz.monitor.model.esModel.EsDataSources;
import com.quartz.monitor.model.postgresqlModel.PostgresqlDataSources;
import com.quartz.monitor.model.redisModel.RedisDataSources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.StandardServletEnvironment;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: tony_jaa
 * @Date: 2018/12/29 15:32
 * @Description:
 */
@Component
@PropertySource(value = "classpath:config/db-conf.properties")
public class ConfigUtil {

    @Autowired
    private Environment environment;


    public void getRedisSourceByConf() {
        Map<String,DateSources> dataSourceHashMap = new HashMap<>();
        StandardServletEnvironment standardServletEnvironment = (StandardServletEnvironment) environment;
        ResourcePropertySource resourcePropertySource =(ResourcePropertySource)  standardServletEnvironment.getPropertySources().get( "class path resource [config/db-conf.properties]" );
        resourcePropertySource.getSource().forEach((k,v)->{
            String[] arr = k.split("\\.");
            String key = arr[arr.length - 1];
            String value = (String) v;
            ConstantParam.propMap.put(k,value);
            String preKey = k.replaceAll(key,"");
            System.out.println("preKey:"+preKey);
            if ( k.contains("db")) {
                DateSources dateSources = dataSourceHashMap.get(preKey);
                if( dateSources == null ) dataSourceHashMap.put(preKey,new PostgresqlDataSources());
                initPropList(DataSourcesAll.POSTGRESQL_DATA_SOURCES_LIST,dataSourceHashMap.get(preKey),key,value);
            }else if( k.contains("redis") ){
                DateSources dateSources = dataSourceHashMap.get(preKey);
                if( dateSources == null ) dataSourceHashMap.put(preKey,new RedisDataSources());
                initPropList(DataSourcesAll.REDIS_DATA_SOURCES_LIST,dataSourceHashMap.get(preKey),key,value);
            }else if( k.contains("es") ){
                DateSources dateSources = dataSourceHashMap.get(preKey);
                if( dateSources == null ) dataSourceHashMap.put(preKey,new EsDataSources());
                initPropList(DataSourcesAll.ES_DATA_SOURCES_LIST,dataSourceHashMap.get(preKey),key,value);
            }else{
                System.out.println("无参数");
            }
        });
    }

    public void initPropList(List list,DateSources dateSources,String key,String value){
        try {
            ReflectUtil.invoke(dateSources,"set"+firstPhareToBig(key),value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if( !list.contains(dateSources) ) list.add(dateSources);
    }

    public String firstPhareToBig( String name ){
       return name.substring(0,1).toUpperCase() + name.substring(1,name.length());
    }
}
